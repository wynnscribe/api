package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializeDungeonKeys() {
    val teleportScrollProject = Projects.getOrCreate(
        Project.Create(
            name = "/Item/DungeonKey",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.item.dungeon-key",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )))?:return
    Originals.getOrCreate(
        projectId = teleportScrollProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <gold>{re:(.+)} Key
            """.trimIndent()),
            stopOnMatch = false,
            template = Project.Original.Template("""
                <gold>{1} Key
            """.trimIndent()),
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = teleportScrollProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><gray>Grants access to the
                <!italic>{re:.*}<white>{re:(.+)}</white><gray> once
            """.trimIndent()),
            stopOnMatch = false,
            template = Project.Original.Template("""
                <!italic><gray>Grants access to the
                <!italic><white>{1}</white><gray> once
            """.trimIndent()),
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = teleportScrollProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Dungeon Info:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = teleportScrollProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Coordinates:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = teleportScrollProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Combat Lv:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
}
