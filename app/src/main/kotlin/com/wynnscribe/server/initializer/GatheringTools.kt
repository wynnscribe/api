package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

val craftings = listOf(
    "Scribing" to "Scribing",
    "Jweling" to "Jweling",
    "Alchemism" to "Alchemism",
    "Cooking" to "Cooking",
    "Weaponsmithing" to "Weaponsmithing",
    "Tailoring" to "Tailoring",
    "Woodworking" to "Woodworking",
    "Armouring" to "Armouring",
)

fun initializeCrafting() {
    val craftingListProject = Projects.getOrCreate(
        Project.Create(
            name = "/Crafting/Name",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.item.crafting-list",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )
        )
    ) ?: return
    craftings.forEach { (stat, template) ->
        Originals.getOrCreate(
            projectId = craftingListProject.id,
            original = Project.Original.Create(
                text = Project.Original.OriginalText(stat),
                stopOnMatch = false,
                template = Project.Original.Template(template),
                children = listOf(),
                type = Project.Original.Type.Damage
            )
        )
    }
}