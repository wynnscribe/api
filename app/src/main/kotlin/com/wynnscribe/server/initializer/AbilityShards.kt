package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializeAbilityShards() {
    val abilityShardProject = Projects.getOrCreate(
        Project.Create(
            name = "/Item/AbilityShard",
            description = "",
            filter = Project.Filter.Create(
                null,
                title = Project.Filter.Content.Create(
                    content = "Ability Shard",
                    withColors = false,
                    fullMatch = true
                ),
                null
            )))?:return
    Originals.getOrCreate(
        projectId = abilityShardProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Ability Shard
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = abilityShardProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><gray>Use this to reset your
                <!italic><dark_purple><white>Ability Tree </white><gray>while in a town.
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = abilityShardProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Quest Req:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
}
