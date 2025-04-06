package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializeMaterials() {
    val materialsProject = Projects.getOrCreate(
        Project.Create(
            name = "/Item/Materials",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.item.material",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )))?:return
    Originals.getOrCreate(
        projectId = materialsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Crafting Material
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = materialsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><gray>Use this material to purchase
                <!italic><dark_purple><gray>blocks in </gray><white>Housing</white><gray>, or to craft
                {re:([\s\S]+)}
            """.trimIndent()),
            template = Project.Original.Template("""
                <!italic><gray>Use this material to purchase
                <!italic><dark_purple><gray>blocks in </gray><white>Housing</white><gray>, or to craft
                {1}
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
}
