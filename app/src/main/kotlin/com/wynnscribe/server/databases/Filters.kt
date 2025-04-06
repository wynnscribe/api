package com.wynnscribe.server.databases

import com.wynnscribe.models.Project
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.updateReturning

object Filters : IdTable<Long>("filters") {
    override val id: Column<EntityID<Long>> = long("id").autoIncrement().entityId()

    val type = varchar("type", 256).nullable().default(null)
    val typeFullMatch = bool("type_full_match").nullable().default(null)
    val typeWithColors = bool("type_with_colors").nullable().default(null)
    val title = varchar("title", 256).nullable().default(null)
    val titleFullMatch = bool("title_full_match").nullable().default(true)
    val titleWithColors = bool("title_with_colors").nullable().default(null)
    val content = varchar("content", 256).nullable().default(null)
    val contentFullMatch = bool("content_full_match").nullable().default(true)
    val contentWithColors = bool("content_with_colors").nullable().default(null)

    override val primaryKey: PrimaryKey = PrimaryKey(id)

    fun get(id: Project.Filter.FilterId): Project.Filter.Impl? {
        return Filters.selectAll().where { Filters.id eq id.value }.singleOrNull()?.let(::filterFrom)
    }

    fun filterFrom(resultRow: ResultRow): Project.Filter.Impl {
        return Project.Filter.Impl(
            id = Project.Filter.FilterId(resultRow[id].value),
            type = resultRow[type]?.ifEmpty { null }?.let {
                Project.Filter.Content.Impl(it, resultRow[typeWithColors] == true, resultRow[typeFullMatch] == true)
            },
            title = resultRow[title]?.ifEmpty { null }?.let {
                Project.Filter.Content.Impl(it, resultRow[titleWithColors] == true, resultRow[titleFullMatch] != false)
            },
            content = resultRow[content]?.ifEmpty { null }?.let {
                Project.Filter.Content.Impl(it, resultRow[contentWithColors] == true, resultRow[contentFullMatch] != false)
            }
        )
    }

    fun has(id: Project.Filter.FilterId): Boolean {
        return Filters.selectAll().where { Filters.id eq id.value }.singleOrNull() != null
    }

    fun all(): List<Project.Filter.Impl> {
        return Filters.selectAll().map(::filterFrom)
    }

    fun update(id: Project.Filter.FilterId, filter: Project.Filter.Update): Project.Filter.Impl? {
        val updated = Filters.updateReturning(where = { Filters.id eq id.value }) {
            it[type] = filter.type?.content?.ifEmpty { null }
            it[typeFullMatch] = filter.type?.fullMatch
            it[typeWithColors] = filter.type?.withColors
            it[title] = filter.title?.content?.ifEmpty { null }
            it[titleFullMatch] = filter.title?.fullMatch
            it[titleWithColors] = filter.title?.withColors
            it[content] = filter.content?.content?.ifEmpty { null }
            it[contentFullMatch] = filter.content?.fullMatch
            it[contentWithColors] = filter.content?.withColors
        }.firstOrNull()
        return filterFrom(updated?:return null)
    }

    fun delete(id: Project.Filter.FilterId) {
        Filters.deleteWhere { Filters.id eq id.value }
    }

    fun create(filter: Project.Filter.Create): Project.Filter.Impl? {
        return Filters.insertReturning {
            it[type] = filter.type?.content
            it[typeFullMatch] = filter.type?.fullMatch
            it[typeWithColors] = filter.type?.withColors
            it[title] = filter.title?.content
            it[titleFullMatch] = filter.title?.fullMatch
            it[titleWithColors] = filter.title?.withColors
            it[content] = filter.content?.content
            it[contentFullMatch] = filter.content?.fullMatch
            it[contentWithColors] = filter.content?.withColors
        }.firstOrNull()?.let(::filterFrom)
    }
}