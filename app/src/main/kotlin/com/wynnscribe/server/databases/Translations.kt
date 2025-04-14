package com.wynnscribe.server.databases

import com.wynnscribe.models.Language
import com.wynnscribe.models.Project
import com.wynnscribe.models.Project.Original.OriginalId
import com.wynnscribe.models.Project.Original.Translation.Status
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchUpsert
import org.jetbrains.exposed.sql.deleteReturning
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestampWithTimeZone
import org.jetbrains.exposed.sql.kotlin.datetime.KotlinOffsetDateTimeColumnType
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.updateReturning
import org.jetbrains.exposed.sql.upsertReturning
import java.time.OffsetDateTime

object Translations : IdTable<Long>("translations") {
    override val id: Column<EntityID<Long>> = long("id").autoIncrement().entityId()

    val originalId = reference("originalId", Originals.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)

    val text = text("text")

    val languageId = reference("languageId", Languages.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)

    val userId = varchar("userId", 64)

    val status = enumeration<Status>("status").default(Status.Pending)

    val createdAt = timestampWithTimeZone("createdAt").defaultExpression(CurrentTimestampWithTimeZone)

    val updatedAt = timestampWithTimeZone("updatedAt").defaultExpression(CurrentTimestampWithTimeZone)

    override val primaryKey: PrimaryKey = PrimaryKey(id)

    init {
        uniqueIndex(originalId, languageId, userId)
        uniqueIndex(originalId, languageId, text)
        index(false, originalId, languageId)
        index(false, originalId)
        index(false, status, languageId)
        index(false, userId)
    }

    fun translationFrom(resultRow: ResultRow): Project.Original.Translation.Impl {
        return Project.Original.Translation.Impl(
            id = Project.Original.Translation.TranslationId(resultRow[id].value),
            originalId = OriginalId(resultRow[originalId].value),
            text = Project.Original.Translation.TranslatedText(resultRow[text]),
            languageId = Language.LanguageId(resultRow[languageId].value),
            userId = Project.UserId(resultRow[userId]),
            status = resultRow[status]
        )
    }

    fun updateStatus(id: Project.Original.Translation.TranslationId, status: Status): Project.Original.Translation.Impl? {
        val updated = Translations.updateReturning(where = { Translations.id eq id.value }) {
            it[Translations.status] = status
        }.firstOrNull()?:return null
        return translationFrom(updated)
    }

    fun get(id: Project.Original.Translation.TranslationId): Project.Original.Translation.Impl? {
        return Translations.selectAll().where { Translations.id eq id.value }.firstOrNull()?.let(::translationFrom)
    }

    fun all(): List<Project.Original.Translation.Impl> {
        return Translations.selectAll().map(::translationFrom)
    }

    fun list(originalIds: List<OriginalId>, languageId: Language.LanguageId): List<Project.Original.Translation.Impl> {
        return Translations.selectAll().where { originalId inList originalIds.map(OriginalId::value) and (Translations.languageId eq languageId.value) }.map(::translationFrom)
    }

    fun list(originalId: OriginalId): List<Project.Original.Translation.Impl> {
        return Translations.selectAll().where { Translations.originalId eq originalId.value }.map(::translationFrom)
    }

    fun list(originalId: OriginalId, languageId: Language.LanguageId): List<Project.Original.Translation.Impl> {
        return Translations.selectAll().where { (Translations.originalId eq originalId.value) and (Translations.languageId eq languageId.value) }.map(::translationFrom)
    }

    fun has(id: Project.Original.Translation.TranslationId): Boolean {
        return Translations.selectAll().where { Translations.id eq id.value }.singleOrNull() != null
    }

    fun delete(id: Project.Original.Translation.TranslationId) {
        Translations.deleteWhere { Translations.id eq id.value }
    }

    fun delete(id: Project.Original.Translation.TranslationId, userId: Project.UserId): Boolean {
        return Translations.deleteReturning { (Translations.id eq id.value) and (Translations.userId eq userId.value) }.firstOrNull() != null
    }

    fun createOrUpdate(translation: Project.Original.Translation.Create, userId: Project.UserId, languageId: Language.LanguageId, originalId: OriginalId): Project.Original.Translation.Impl? {
        val inserted = Translations.upsertReturning(keys = arrayOf(Translations.originalId, Translations.userId, Translations.languageId)) {
            it[Translations.languageId] = languageId.value
            it[Translations.userId] = userId.value
            it[Translations.originalId] = originalId.value
            it[text] = translation.text.value
            it[status] = translation.status
            it[Translations.updatedAt] = OffsetDateTime.now()
        }.firstOrNull()?.let(::translationFrom)?:return null
        return inserted
    }

    fun batchCreateOrUpdate(translations: List<Project.Original.Translation.CreateMany>, userId: Project.UserId, languageId: Language.LanguageId): Project.Original.Translation.Impl? {
        val inserted = Translations.batchUpsert(translations,keys = arrayOf(Translations.originalId, Translations.userId, Translations.languageId)) {
            this[Translations.languageId] = languageId.value
            this[Translations.userId] = userId.value
            this[Translations.originalId] = it.originalId.value
            this[Translations.text] = it.text.value
            this[Translations.status] = it.status
            this[Translations.updatedAt] = OffsetDateTime.now()
        }.firstOrNull()?.let(::translationFrom)?:return null
        return inserted
    }
}