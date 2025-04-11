package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

val gatherings = listOf(
    "Fishing" to "Fishing",
    "Woodcutting" to "Woodcutting",
    "Mining" to "Mining",
    "Farming" to "Farming",
)

fun initializeGatheringTools() {
    val gatheringListProject = Projects.getOrCreate(
        Project.Create(
            name = "/Gathering/Name",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.item.gathering-list",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )
        )
    ) ?: return
    gatherings.forEach { (stat, template) ->
        Originals.getOrCreate(
            projectId = gatheringListProject.id,
            original = Project.Original.Create(
                text = Project.Original.OriginalText(stat),
                stopOnMatch = false,
                template = Project.Original.Template(template),
                children = listOf(),
                type = Project.Original.Type.Damage
            )
        )
    }
    val gatheringToolProject = Projects.getOrCreate(
        Project.Create(
            name = "/Item/GatheringTool",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.item.gathering-tool",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )
        )
    ) ?: return
    Originals.getOrCreate(
        projectId = gatheringToolProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Gathering {in:#wynnscribe.gear.names} {re:(.+)}
            """.trimIndent()),
            template = Project.Original.Template("""
                Gathering {in:1} {1}
            """.trimIndent()),
            stopOnMatch = false,
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = gatheringToolProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Tier {re:(\d+)}
            """.trimIndent()),
            template = Project.Original.Template("""
                Tier {1}
            """.trimIndent()),
            stopOnMatch = false,
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = gatheringToolProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Gathering Speed: {re:(\d)+}</gold><gray> ({in:#wynnscribe.speeds})
            """.trimIndent()),
            template = Project.Original.Template("""
                Gathering Speed: {1}</gold><gray> ({in:1})
            """.trimIndent()),
            stopOnMatch = false,
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = gatheringToolProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <dark_aqua>Gathering Tool </dark_aqua><dark_gray>[{re:(.+)} Durability]
            """.trimIndent()),
            template = Project.Original.Template("""
                <dark_aqua>Gathering Tool </dark_aqua><dark_gray>[{re:(.+)} Durability]
            """.trimIndent()),
            stopOnMatch = false,
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = gatheringToolProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                {in:#wynnscribe.item.gathering-list} Lv. Min: {re:(\d+)}
            """.trimIndent()),
            template = Project.Original.Template("""
                {in:1} Lv. Min: {1}
            """.trimIndent()),
            stopOnMatch = false,
            type = Project.Original.Type.GameItem
        )
    )
}