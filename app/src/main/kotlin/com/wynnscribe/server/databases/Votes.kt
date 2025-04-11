package com.wynnscribe.server.databases

import com.wynnscribe.models.Project
import com.wynnscribe.models.Project.Original.Translation.TranslationId
import com.wynnscribe.models.Project.Original.Translation.Vote
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestampWithTimeZone
import org.jetbrains.exposed.sql.kotlin.datetime.timestampWithTimeZone
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.upsertReturning
import java.time.OffsetDateTime

object Votes : Table("votes") {
    val userId = varchar("user_id", 256)
    val translationId = reference("translation_id", Translations.id, onUpdate = ReferenceOption.CASCADE, onDelete = ReferenceOption.CASCADE)
    val type = enumeration<Vote.Type>("type")
    val createdAt = timestampWithTimeZone("createdAt").defaultExpression(CurrentTimestampWithTimeZone)
    val updatedAt = timestampWithTimeZone("updatedAt").defaultExpression(CurrentTimestampWithTimeZone)

    init {
        uniqueIndex(userId, translationId)
        index(false, translationId)
    }

    fun voteFrom(resultRow: ResultRow): Vote.Impl {
        return Vote.Impl(
            userId = Project.UserId(resultRow[userId]),
            translationId = TranslationId(resultRow[translationId].value),
            type = resultRow[type]
        )
    }

    fun all(): List<Vote.Impl> {
        return Votes.selectAll().map(::voteFrom)
    }

    fun list(translationIds: List<TranslationId>): List<Vote.Impl> {
        return Votes.selectAll().where { translationId inList translationIds.map(TranslationId::value) }.map(::voteFrom)
    }

    fun list(translationId: TranslationId): List<Vote.Impl> {
        return Votes.selectAll().where { Votes.translationId eq translationId.value }.map(::voteFrom)
    }

    fun createOrUpdate(vote: Vote.Create, userId: Project.UserId, translationId: TranslationId): Vote.Impl? {
        val inserted = Votes.upsertReturning(keys = arrayOf(Votes.translationId, Votes.userId)) {
            it[Votes.translationId] = translationId.value
            it[Votes.userId] = userId.value
            it[Votes.type] = vote.type
            it[Votes.updatedAt] = OffsetDateTime.now()
        }.firstOrNull()?.let(::voteFrom)?:return null
        return inserted
    }
}