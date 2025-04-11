package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializeGeneralItems() {
    val generalItemsProject = Projects.getOrCreate(
        Project.Create(
            name = "/Item/General",
            description = "",
            ))?:return

    Originals.getOrCreate(
        projectId = generalItemsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><#82eff4>Ability Shard
                    <!italic><gray>Use this to reset your
                    <!italic><dark_purple><white>Ability Tree </white><gray>while in a town.
                    <!italic><dark_purple> 
                    {re:(.+)}
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    <!italic><#82eff4>Ability Shard
                    <!italic><gray>Use this to reset your
                    <!italic><dark_purple><white>Ability Tree </white><gray>while in a town.
                    <!italic><dark_purple> 
                    {1}
                    """.trimIndent()
            ),
            stopOnMatch = true,
            children = listOf(
                Project.Original.Create(
                    text = Project.Original.OriginalText("Quest Req:"),
                    stopOnMatch = false,
                    type = Project.Original.Type.GameItem
                )
            ),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "Ability Shard",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            ),
            type = Project.Original.Type.GameItem
        )
    )
    
    Originals.getOrCreate(
        projectId = generalItemsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><gold>Ingredient Pouch
                    <!italic><dark_purple> 
                    <!italic><dark_purple><white>Left-Click</white><gray> to view contents
                    <!italic><dark_purple><white>Shift Right-Click</white><gray> to sell (</gray>{re:(.+)}<gray>)
                    {re:([\s\S]+)?}
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    <!italic><gold>Ingredient Pouch
                    <!italic><dark_purple> 
                    <!italic><dark_purple><white>Left-Click</white><gray> to view contents
                    <!italic><dark_purple><white>Shift Right-Click</white><gray> to sell (</gray>{1}<gray>)
                    {2}
                    """.trimIndent()
            ),
            stopOnMatch = true,
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "Ingredient Pouch",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            ),
            type = Project.Original.Type.GameItem
        )
    )
    
    Originals.getOrCreate(
        projectId = generalItemsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><gold>Ingredient Pouch
                    <!italic><dark_purple> 
                    <!italic><dark_purple><white>Left-Click</white><gray> to view contents
                    <!italic><dark_purple> 
                    <!italic><gray>(Your pouch is empty)
                    """.trimIndent()
            ),
            stopOnMatch = true,
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "Ingredient Pouch",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            ),
            type = Project.Original.Type.GameItem
        )
    )
    
    Originals.getOrCreate(
        projectId = generalItemsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><aqua>Character Info
                    <!italic><font:invisible><gray>b6ad241b
                    <!italic><gray>View your current character
                    <!italic><gray>information, assign skill points,
                    <!italic><gray>access your ability tree, and more
                    <!italic><dark_purple> 
                    <!italic><dark_purple><dark_green>Unassigned Skill Points: </dark_green><white>{re:(.+)}
                    <!italic><dark_purple><dark_aqua>✦ Unused Ability Points: </dark_aqua><white>{re:(.+)}
                    <!italic><dark_purple> 
                    <!italic><white><font:keybind></font><green> Click to Open
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    <!italic><aqua>Character Info
                    <!italic><font:invisible><gray>b6ad241b
                    <!italic><gray>View your current character
                    <!italic><gray>information, assign skill points,
                    <!italic><gray>access your ability tree, and more
                    <!italic><dark_purple> 
                    <!italic><dark_purple><dark_green>Unassigned Skill Points: </dark_green><white>{1}
                    <!italic><dark_purple><dark_aqua>✦ Unused Ability Points: </dark_aqua><white>{2}
                    <!italic><dark_purple> 
                    <!italic><white><font:keybind></font><green> Click to Open
                    """.trimIndent()
            ),
            stopOnMatch = true,
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "Character Info",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            ),
            type = Project.Original.Type.GameItem
        )
    )
    
    Originals.getOrCreate(
        projectId = generalItemsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><light_purple>Content Book
                    <!italic><gray>Hold and Right-Click to see content
                    <!italic><dark_purple> 
                    <!italic><dark_purple>Quests: <light_purple>{re:(.+)} <dark_purple>[{re:(.+)}%]
                    <!italic><dark_purple> 
                    <!italic><dark_purple>Discoveries:
                    <!italic><dark_purple>- <gray>Territorial: <white>{re:(.+)} <dark_purple>[{re:(.+)}%]
                    <!italic><dark_purple>- <gold>World: <yellow>{re:(.+)} <dark_purple>[{re:(.+)}%]
                    <!italic><dark_purple>- <dark_aqua>Secret: <aqua>{re:(.+)} <dark_purple>[{re:(.+)}%]
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    <!italic><light_purple>Content Book
                    <!italic><gray>Hold and Right-Click to see content
                    <!italic><dark_purple> 
                    <!italic><dark_purple>Quests: <light_purple>{1} <dark_purple>[{2}%]
                    <!italic><dark_purple> 
                    <!italic><dark_purple>Discoveries:
                    <!italic><dark_purple>- <gray>Territorial: <white>{3} <dark_purple>[{4}%]
                    <!italic><dark_purple>- <gold>World: <yellow>{5} <dark_purple>[{6}%]
                    <!italic><dark_purple>- <dark_aqua>Secret: <aqua>{7} <dark_purple>[{8}%]
                    """.trimIndent()
            ),
            stopOnMatch = true,
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "Content Book",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            ),
            type = Project.Original.Type.GameItem
        )
    )
}