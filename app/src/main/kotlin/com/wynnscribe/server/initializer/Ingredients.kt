package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializeIngredients() {
    val ingredientsProject = Projects.getOrCreate(
        Project.Create(
            name = "/Item/Ingredients",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.item.ingredient",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )))?:return
    Originals.getOrCreate(
        projectId = ingredientsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Crafting Ingredient
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = ingredientsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                To ingredients to the left of this one
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = ingredientsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                To ingredients to the right of this one
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = ingredientsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                To ingredients above this one
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = ingredientsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                To ingredients under this one
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = ingredientsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                To ingredients touching this one
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = ingredientsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                To ingredients not touching this one
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = ingredientsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Ingredient Effectiveness
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = ingredientsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                {re:(.+)} Duration
            """.trimIndent()),
            template = Project.Original.Template("""
                {1} Duration
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = ingredientsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                {re:(.+)} Durability
            """.trimIndent()),
            template = Project.Original.Template("""
                {1} Durability
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = ingredientsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                {re:(.+)} {in:#wynnscribe.stats}
            """.trimIndent()),
            template = Project.Original.Template("""
                {1} {in:1}
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = ingredientsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                {in:#wynnscribe.status} Min.
            """.trimIndent()),
            template = Project.Original.Template("""
                {in:1} Min.
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = ingredientsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Crafting Lv. Min:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
}
