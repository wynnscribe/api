package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializePowders() {
    val gearBoxProject = Projects.getOrCreate(
        Project.Create(
            name = "/Item/Powders",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.item.powder",
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
                {in:#wynnscribe.status} Powder
            """.trimIndent()),
            stopOnMatch = false,
            template = Project.Original.Template("""
                {in:1} Powder
            """.trimIndent()),
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    
    Originals.getOrCreate(
        projectId = gearBoxProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Effect on Weapons:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    
    Originals.getOrCreate(
        projectId = gearBoxProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Effect on Armour:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    
    Originals.getOrCreate(
        projectId = gearBoxProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Ingredient Effectiveness:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    
    Originals.getOrCreate(
        projectId = gearBoxProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Ingredient Effectiveness:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    
    Originals.getOrCreate(
        projectId = gearBoxProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><dark_gray>Add this powder to your items
                <!italic><dark_gray>by visiting a Powder Master
                <!italic><dark_gray>or use it as an ingredient
                <!italic><dark_gray>when crafting.
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    
    Originals.getOrCreate(
        projectId = gearBoxProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><dark_gray>Adding 2 powders of tier 4-6
                <!italic><dark_gray>at the powder master will
                <!italic><dark_gray>unlock a special attack/effect.
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
}
