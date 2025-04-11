package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializeEmeraldPouch() {
    val emeraldPouch = Projects.getOrCreate(
        Project.Create(
            name = "/Item/EmeraldPouch",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.item.emerald-pouch",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )))?:return
    Originals.getOrCreate(
        projectId = emeraldPouch.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <green>Emerald Pouch</green><dark_green> [Tier {re:(.+)}]
            """.trimIndent()),
            stopOnMatch = false,
            template = Project.Original.Template("""
                <green>Emerald Pouch</green><dark_green> [Tier {1}]
            """.trimIndent()),
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = emeraldPouch.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                - {re:(\d+)} Rows </gray><dark_gray>({re:(.+)} Total)
            """.trimIndent()),
            stopOnMatch = false,
            template = Project.Original.Template("""
                <gray>- {1} Rows </gray><dark_gray>({2} Total)
            """.trimIndent()),
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = emeraldPouch.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <gray>- Converts up to </gray>{re:(.+)}
            """.trimIndent()),
            stopOnMatch = false,
            template = Project.Original.Template("""
                <gray>- Converts up to </gray>{1}
            """.trimIndent()),
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = emeraldPouch.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <white>Right-Click </white><gray>to view contents
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = emeraldPouch.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                - No Auto-Conversions
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = emeraldPouch.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><gray>Emerald Pouches allows the wearer
                <!italic><dark_purple><gray>to easily </gray><aqua>store</aqua><gray> and </gray><aqua>convert</aqua><gray> picked
                <!italic><gray>emeralds without spending extra
                <!italic><gray>inventory slots.
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
}