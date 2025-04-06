package com.wynnscribe.server.databases

import com.wynnscribe.models.Language
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertReturning
import org.jetbrains.exposed.sql.selectAll

object Languages : IdTable<String>("languages") {
    override val id: Column<EntityID<String>> = varchar("id", 32).entityId()

    val name = varchar("name", 32)

    val englishName = varchar("englishName", 32)

    val emoji = varchar("emoji", 8)

    val color = varchar("color", 16)

    override val primaryKey: PrimaryKey = PrimaryKey(id)

    fun languageFrom(resultRow: ResultRow): Language.Impl {
        return Language.Impl(
            id = Language.LanguageId(resultRow[id].value),
            name = resultRow[name],
            englishName = resultRow[englishName],
            emoji = resultRow[emoji],
            color = resultRow[color]
        )
    }

    fun get(id: Language.LanguageId): Language.Impl? {
        return Languages.selectAll().where { Languages.id eq id.value }.firstOrNull()?.let(::languageFrom)
    }

    fun all(): List<Language.Impl> {
        return Languages.selectAll().map(::languageFrom)
    }

    fun has(id: Language.LanguageId): Boolean {
        return Languages.selectAll().where { Languages.id eq id.value }.singleOrNull() != null
    }

    fun create(language: Language.Create): Language.Impl? {
        return Languages.insertReturning {
            it[id] = language.id.value
            it[name] = language.name
            it[englishName] = language.englishName
            it[emoji] = language.emoji
            it[color] = language.color
        }.firstOrNull()?.let(::languageFrom)
    }

    fun delete(id: Language.LanguageId) {
        Languages.deleteWhere { Languages.id eq id.value }
    }
}