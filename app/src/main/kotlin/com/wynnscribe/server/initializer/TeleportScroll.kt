package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializeTeleportScroll() {
    val teleportScrollProject = Projects.getOrCreate(
        Project.Create(
            name = "/Item/TeleportScroll",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.item.teleport-scroll",
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
                <#8193ff>{re:(.+)} Teleportation Scroll <#f9e79e>[{re:(.+)}]
            """.trimIndent()),
            stopOnMatch = false,
            template = Project.Original.Template("""
                <#8193ff>{1} Teleportation Scroll <#f9e79e>[{2}]
            """.trimIndent()),
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = teleportScrollProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Warps to <font:banner/box>{re:(.+)}
            """.trimIndent()),
            stopOnMatch = false,
            template = Project.Original.Template("""
                Warps to <font:banner/box>{1}</font>
            """.trimIndent()),
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = teleportScrollProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <font:space>󐀅</font><font:keybind></font> Right-Click to use
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
                Charges are restored every 10m
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
                <!italic><gray>Teleportation scrolls can be used
                <!italic><gray>for quick travelling across the world
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
}
