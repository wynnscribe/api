package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

val speeds = listOf(
    "Super Slow" to "Super Slow",
    "Super Fast" to "Super Fast",
    "Very Slow" to "Very Slow",
    "Very Fast" to "Very Fast",
    "Slow" to "Slow",
    "Fast" to "Fast",
    "Normal" to "Normal"
)

fun initializeSpeeds() {
    val speedsProject = Projects.getOrCreate(
        Project.Create(
            name = "/Speeds",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.speeds",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )))?:return

    speeds.forEach { (stat, template) ->
        Originals.getOrCreate(
            projectId = speedsProject.id,
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