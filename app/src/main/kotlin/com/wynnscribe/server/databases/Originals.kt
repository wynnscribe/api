package com.wynnscribe.server.databases

import com.wynnscribe.models.Language
import com.wynnscribe.models.Project
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteReturning
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.updateReturning

object Originals : IdTable<Long>("originals") {
    override val id: Column<EntityID<Long>> = long("id").autoIncrement().entityId()

    val text = text("text")

    val stopOnMatch = bool("stopOnMatch").default(false)

    val template = text("template").nullable().default(null)

    val filterId = reference("filterId", Filters.id, onDelete = ReferenceOption.SET_NULL, onUpdate = ReferenceOption.CASCADE)

    val parentId = reference("parentId", Originals.id).default(EntityID(-1L, Originals))

    val projectId = reference("projectId", Projects.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)

    val type = enumeration<Project.Original.Type>("type")

    override val primaryKey: PrimaryKey = PrimaryKey(id)

    init {
        index(false, projectId)
        index(true, projectId, text, parentId)
    }


    fun originalFrom(resultRow: ResultRow): Project.Original.Impl {
        return Project.Original.Impl(
            id = Project.Original.OriginalId(resultRow[id].value),
            text = Project.Original.OriginalText(resultRow[text]),
            stopOnMatch = resultRow[stopOnMatch],
            template = resultRow[template]?.ifEmpty { null }?.let(Project.Original::Template),
            filterId = resultRow[filterId].value.let(Project.Filter::FilterId),
            parentId = resultRow[parentId].value.takeIf { it >= 0 }?.let(Project.Original::OriginalId),
            projectId = Project.ProjectId(resultRow[projectId].value),
            type = resultRow[type]
        )
    }

    fun get(projectId: Project.ProjectId ,text: Project.Original.OriginalText, parentId: Project.Original.OriginalId?): Project.Original.Impl? {
        return Originals.selectAll().where { (Originals.text eq text.value) and (Originals.projectId eq projectId.value) and (if(parentId == null) (Originals.parentId.isNull() or (Originals.parentId eq -1)) else (Originals.parentId eq parentId.value)) }.singleOrNull()?.let(::originalFrom)
    }

    fun get(id: Project.Original.OriginalId): Project.Original.Impl? {
        return Originals.selectAll().where { Originals.id eq id.value }.singleOrNull()?.let(::originalFrom)
    }

    fun translationDetails(id: Project.Original.OriginalId, languageId: Language.LanguageId): List<Project.Original.Translation.Details> {
        val translations = Translations.list(id, languageId = languageId)
        val votes = Votes.list(translations.map(Project.Original.Translation::id))
        return translations.map { Project.Original.Translation.Details.of(it, votes) }
    }

    fun list(projectId: Project.ProjectId, limit: Int, offset: Long): List<Project.Original.Impl> {
        return Originals.selectAll().where { Originals.projectId eq projectId.value }.limit(limit).offset(offset).map(::originalFrom)
    }

    fun has(id: Project.Original.OriginalId): Boolean {
        return Originals.selectAll().where { Originals.id eq id.value }.singleOrNull() != null
    }

    fun list(parentId: Project.Original.OriginalId): List<Project.Original.Impl> {
        return Originals.selectAll().where { Originals.parentId eq parentId.value }.map(::originalFrom)
    }

    fun delete(id: Project.Original.OriginalId): Boolean {
        val original = originalFrom(Originals.deleteReturning { Originals.id eq id.value }.firstOrNull()?:return false)
        Filters.delete(original.filterId)
        val parentId = original.parentId
        if(parentId != null) {
            this.list(parentId).forEach { original -> this.delete(original.id) }
        }
        return true
    }

    fun all(): List<Project.Original.Impl> {
        return Originals.selectAll().map(::originalFrom)
    }

    fun update(id: Project.Original.OriginalId, original: Project.Original.Update): Project.Original.Impl? {
        val updated = Originals.updateReturning(where = { Originals.id eq id.value }) {
            it[text] = original.text.value
            it[stopOnMatch] = original.stopOnMatch
            it[template] = original.template?.value?.ifEmpty { null }
            it[type] = original.type
        }.firstOrNull()?.let(::originalFrom)?:return null
        Filters.update(updated.filterId, original.filter)
        return updated
    }

    fun create(original: Project.Original.Create, projectId: Project.ProjectId): Project.Original.Impl? {
        val inserted = Originals.insertReturning {
            it[text] = original.text.value
            it[stopOnMatch] = original.stopOnMatch
            it[template] = original.template?.value?.ifEmpty { null }
            it[filterId] = Filters.create(original.filter)!!.id.value
            it[type] = original.type
            it[Originals.projectId] = projectId.value
            if(original.parentId != null && original.parentId!!.value >= 0) {
                it[parentId] = original.parentId!!.value
            }
        }.firstOrNull()?.let(::originalFrom)

        original.children?.forEach { child ->
            Originals.insert {
                it[text] = child.text.value
                it[stopOnMatch] = child.stopOnMatch
                it[template] = child.template?.value?.ifEmpty { null }
                it[filterId] = Filters.create(child.filter)!!.id.value
                it[type] = original.type
                it[Originals.projectId] = projectId.value
                if(child.parentId != null && child.parentId!!.value >= 0) {
                    it[parentId] = child.parentId!!.value
                } else {
                    it[parentId] = inserted?.id?.value?:-1L
                }
            }
        }
        return inserted
    }

    fun getOrCreate(original: Project.Original.Create, projectId: Project.ProjectId): Project.Original.Impl? {
        val org = this.get(projectId = projectId, text = original.text, parentId = original.parentId)
        if(org != null) return org
        return this.create(original, projectId)
    }
}