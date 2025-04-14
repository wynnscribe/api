package com.wynnscribe.server.databases

import com.wynnscribe.models.Language
import com.wynnscribe.models.Project
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.*

object Projects : IdTable<Long>("projects") {
    override val id: Column<EntityID<Long>> = long("id").autoIncrement().entityId()

    val filterId = reference("filterId", Filters.id, onDelete = ReferenceOption.SET_NULL, onUpdate = ReferenceOption.CASCADE)

    val name = varchar("name", 256)

    val description = varchar("description", 256)

    override val primaryKey: PrimaryKey = PrimaryKey(id)

    fun projectFrom(resultRow: ResultRow): Project.Impl {
        return Project.Impl(
            id = Project.ProjectId(resultRow[id].value),
            name = resultRow[name],
            description = resultRow[description],
            filterId = resultRow[filterId].value.let(Project.Filter::FilterId)
        )
    }

    /**
     * ⚠️: 非常に重い処理です。キャッシュなどを残すなどして最低限の使用回数にしてください。
     */
    fun allJson(): Map<Language.LanguageId, List<Project.Json>> {
        val languages = Languages.all()
        val projects = this.all()
        val filters = Filters.all()
        val originals = Originals.all()
        val translations = Translations.all().groupBy { it.languageId }
        val votes = Votes.all()
        return languages.map(Language::id).associateWith { languageId ->
            projects.map { project -> Project.Json.of(
                project = project,
                filter = filters.find { it.id == project.filterId }?: Project.Filter.Json(null, null, null),
                filters = filters,
                originals = originals,
                translations = translations.getOrElse(languageId) { emptyList() },
                votes = votes
            ) }
        }
    }

    fun get(name: String): Project.Impl? {
        return Projects.selectAll().where { Projects.name eq name }.firstOrNull()?.let(::projectFrom)
    }

    fun get(id: Project.ProjectId): Project.Impl? {
        return Projects.selectAll().where { Projects.id eq id.value }.firstOrNull()?.let(::projectFrom)
    }

    fun has(id: Project.ProjectId): Boolean {
        return Projects.selectAll().where { Projects.id eq id.value }.singleOrNull() != null
    }

    fun update(id: Project.ProjectId, project: Project.Update): Project.Impl? {
        val updated = Projects.updateReturning(where = { Projects.id eq id.value }) {
            it[name] = project.name
            it[description] = project.description
            it[filterId] = filterId
        }.firstOrNull()?.let(::projectFrom)?:return null
        Filters.update(updated.filterId, project.filter)
        return updated
    }

    fun all(): List<Project.Impl> {
        return Projects.selectAll().map(::projectFrom)
    }

    fun delete(id: Project.ProjectId): Boolean {
        val project = projectFrom(Projects.deleteReturning { Projects.id eq id.value }.firstOrNull()?:return false)
        Filters.delete(project.filterId)
        return true
    }

    fun create(project: Project.Create): Project.Impl? {
        return Projects.insertReturning {
            it[filterId] = Filters.create(project.filter)!!.id.value
            it[name] = project.name
            it[description] = project.description
        }.firstOrNull()?.let(::projectFrom)
    }

    fun getOrCreate(project: Project.Create): Project.Impl? {
        val prj = this.get(project.name)
        if(prj != null) return prj
        return this.create(project)
    }
}