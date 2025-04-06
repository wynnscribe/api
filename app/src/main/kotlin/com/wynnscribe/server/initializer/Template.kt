package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun template() {
    val gearBoxProject = Projects.getOrCreate(
        Project.Create(
            name = "/Item/GearBox",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.item.gear-box",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )))?:return
    Originals.getOrCreate(
        projectId = gearBoxProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Unidentified {in:#wynnscribe.gear.names}
            """.trimIndent()),
            stopOnMatch = false,
            template = Project.Original.Template("""
                Unidentified {in:1}
            """.trimIndent()),
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = gearBoxProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><gray>This item's powers have
                <!italic><gray>been sealed. Take it to
                <!italic><gray>an identifier to unlock
                <!italic><gray>its potential.
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
}
