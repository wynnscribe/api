package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializeHorse() {
    val horseProject = Projects.getOrCreate(
        Project.Create(
            name = "/Item/Horse",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.item.horse",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )))?:return
    Originals.getOrCreate(
        projectId = horseProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Tier
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = horseProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Speed:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = horseProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                None
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = horseProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Jump:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = horseProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Armour:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = horseProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Xp:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = horseProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Name:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
}
