package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializeIngredientsPouch() {
    val ingredientsPouchProject = Projects.getOrCreate(
        Project.Create(
            name = "/UserInterface/IngredientPouch",
            description = "",
            filter = Project.Filter.Create(
                null,
                null,
                null
            )))?:return
    val ingredientPouch = Originals.getOrCreate(
        projectId = ingredientsPouchProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Ingredient Pouch
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                null,
                title = Project.Filter.Content.Create(
                    content = "Ingredient Pouch",
                    withColors = false,
                    fullMatch = true
                ),
                null
            ),
            type = Project.Original.Type.GameItem
        )
    )!!
    Originals.getOrCreate(
        projectId = ingredientsPouchProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <white>Left-Click</white><gray> to view contents
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem,
            parentId = ingredientPouch.id
        ),
    )
    Originals.getOrCreate(
        projectId = ingredientsPouchProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                (Your pouch is empty)
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem,
            parentId = ingredientPouch.id
        )
    )
    
    Originals.getOrCreate(
        projectId = ingredientsPouchProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <white>Shift Right-Click</white><gray> to sell ({re:(.+)})
            """.trimIndent()),
            template = Project.Original.Template("""
                <white>Shift Right-Click</white><gray> to sell ({1})
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem,
            parentId = ingredientPouch.id
        )
    )
    
    val selling = Originals.getOrCreate(
        projectId = ingredientsPouchProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Click to confirm
            """.trimIndent()),
            filter = Project.Filter.Create(
                null,
                title = Project.Filter.Content.Create(
                    content = "Click to confirm",
                    withColors = false,
                    fullMatch = true
                ),
                null
            ),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )!!
    
    Originals.getOrCreate(
        projectId = ingredientsPouchProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <gray>Selling for: </gray>{re:(.+)}
            """.trimIndent()),
            template = Project.Original.Template("""
                <gray>Selling for: </gray>{1}
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            parentId = selling.id,
            type = Project.Original.Type.GameItem
        )
    )
}
