package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

val gears = mapOf(
    "Helmet" to "Helmet",
    "Chestplate" to "Chestplate",
    "Leggings" to "Leggings",
    "Boots" to "Boots",

    "Bow" to "Bow",
    "Relik" to "Relik",
    "Wand" to "Wand",
    "Dagger" to "Dagger",
    "Spear" to "Spear",

    "Necklace" to "Necklace",
    "Ring" to "Ring",
    "Bracelet" to "Bracelet",

    "Axe" to "Axe",
    "Pickaxe" to "Pickaxe",
    "Rod" to "Rod",
    "Scythe" to "Scythe",
)

fun initializeGears() {
    val gearNamesProject = Projects.getOrCreate(
        Project.Create(
            name = "/Gear/Name",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.gear.names",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )))?:return
    val gearInfosProject = Projects.getOrCreate(
        Project.Create(
            name = "/Item/Gear",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.item.gear",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )))?:return
    gears.forEach { (stat, template) ->
        Originals.getOrCreate(
            projectId = gearNamesProject.id,
            original = Project.Original.Create(
                text = Project.Original.OriginalText(stat),
                stopOnMatch = true,
                template = Project.Original.Template(template),
                children = listOf(),
                type = Project.Original.Type.GameItem
            )
        )
    }
    Originals.getOrCreate(
        projectId = gearInfosProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                {in:#wynnscribe.status} Min
            """.trimIndent()),
            stopOnMatch = false,
            template = Project.Original.Template("""
                {in:1} Min
            """.trimIndent()),
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = gearInfosProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                {re:([a-zA-Z]+)} Lv. Min
            """.trimIndent()),
            stopOnMatch = false,
            template = Project.Original.Template("""
                Combat Lv. Min:
            """.trimIndent()),
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = gearInfosProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                {re:([✤✦❉✹❋✣]+)} {in:#wynnscribe.damages}{re:((?:<.+?>)*)?} Damage
            """.trimIndent()),
            stopOnMatch = false,
            template = Project.Original.Template("""
                {1} {in:1}{2} Damage
            """.trimIndent()),
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = gearInfosProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                {re:([✤✦❉✹❋✣]+)} {in:#wynnscribe.damages}{re:((?:<.+?>)*)?} Defence
            """.trimIndent()),
            stopOnMatch = false,
            template = Project.Original.Template("""
                {1} {in:1}{2} Defence
            """.trimIndent()),
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = gearInfosProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                {re:([+-].+?)} {in:#wynnscribe.stats}
            """.trimIndent()),
            stopOnMatch = false,
            template = Project.Original.Template("""
                {1} {in:1}
            """.trimIndent()),
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = gearInfosProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Class Req:
            """.trimIndent()),
            stopOnMatch = false,
            template = Project.Original.Template("""
                Class Req:
            """.trimIndent()),
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = gearInfosProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Average DPS:
            """.trimIndent()),
            stopOnMatch = false,
            template = Project.Original.Template("""
                Average DPS:
            """.trimIndent()),
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = gearInfosProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("{in:#wynnscribe.speeds} Attack Speed"),
            template = Project.Original.Template("{in:1} Attack Speed"),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = gearInfosProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("[{re:(.+)}] Powder Slots"),
            template = Project.Original.Template("[{1}] Powder Slots"),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.GameItem
        )
    )
}