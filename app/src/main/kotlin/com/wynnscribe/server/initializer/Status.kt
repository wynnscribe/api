package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

val status = mapOf(
    "Strength" to "Strength",
    "Dexterity" to "Dexterity",
    "Intelligence" to "Intelligence",
    "Defence" to "Defence",
    "Agility" to "Agility",
)

fun initializeStatus() {
    val statusProject = Projects.getOrCreate(
        Project.Create(
            name = "/Status",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.status",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )))?:return
    status.forEach { (status, template) ->
        Originals.getOrCreate(
            projectId = statusProject.id,
            original = Project.Original.Create(
                text = Project.Original.OriginalText(status),
                stopOnMatch = true,
                template = Project.Original.Template(template),
                children = listOf(),
                type = Project.Original.Type.Status
            )
        )
    }
}