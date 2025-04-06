package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

val damages = listOf(
    "Earth" to "Earth",
    "Thunder" to "Thunder",
    "Water" to "Water",
    "Fire" to "Fire",
    "Air" to "Air",
    "Neutral" to "Neutral",
    "Main Attack" to "Main Attack",
    "Elemental" to "Elemental",
    "Spell" to "Spell"
)

fun initializeDamages() {
    val damagesProject = Projects.getOrCreate(
        Project.Create(
            name = "/Damages",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.damages",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )))?:return

    damages.forEach { (stat, template) ->
        Originals.getOrCreate(
            projectId = damagesProject.id,
            original = Project.Original.Create(
                text = Project.Original.OriginalText(stat),
                stopOnMatch = true,
                template = Project.Original.Template(template),
                children = listOf(),
                type = Project.Original.Type.Damage
            )
        )
    }
}