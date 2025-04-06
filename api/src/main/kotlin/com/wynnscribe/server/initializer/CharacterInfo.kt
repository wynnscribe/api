package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializeCharacterInfo() {
    val characterInfoProject = Projects.getOrCreate(
        Project.Create(
            name = "/UserInterface/CharacterInfo/Button",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "\uDAFF\uDFDC\uE003",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )))?:return

    Originals.getOrCreate(
        projectId = characterInfoProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><bold><dark_green>Skill Crystal
                    <!italic><dark_purple> 
                    <!italic><dark_purple><gray>You have </gray><green>{re:(.+)}</green><gray> skill points
                    <!italic><gray>to be distributed
                    <!italic><dark_purple> 
                    <!italic><yellow>Shift-Click to reset
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    <!italic><bold><dark_green>Skill Crystal
                    <!italic><dark_purple> 
                    <!italic><dark_purple><gray>You have </gray><green>{1}</green><gray> skill points
                    <!italic><gray>to be distributed
                    <!italic><dark_purple> 
                    <!italic><yellow>Shift-Click to reset
                    """.trimIndent()
            ),
            stopOnMatch = true,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )

    Originals.getOrCreate(
        projectId = characterInfoProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><bold><green>Recruit a Friend
                    <!italic><gray>Recruit friends and get
                    <!italic><gray>exclusive rewards!
                    <!italic><dark_purple> 
                    <!italic><dark_purple><dark_green>✦ Recruited Friends: </dark_green><white>{re:(.+)}
                    <!italic><dark_purple> 
                    <!italic><green>Click to Open
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    <!italic><bold><green>Recruit a Friend
                    <!italic><gray>Recruit friends and get
                    <!italic><gray>exclusive rewards!
                    <!italic><dark_purple> 
                    <!italic><dark_purple><dark_green>✦ Recruited Friends: </dark_green><white>{1}
                    <!italic><dark_purple> 
                    <!italic><green>Click to Open
                    """.trimIndent()
            ),
            stopOnMatch = true,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )

    val dailyRewards = Originals.getOrCreate(
        projectId = characterInfoProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><bold><gold>Daily Reward
                    <!italic><gray>Come back every day for
                    <!italic><gray>amazing daily rewards
                    <!italic><dark_purple> 
                    <!italic><dark_purple><yellow>✦ Streak Multiplier: </yellow><white>{re:(.+)}
                    <!italic><dark_purple> 
                    <!italic><red>Available in {re:(.+)}
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    <!italic><bold><gold>Daily Reward
                    <!italic><gray>Come back every day for
                    <!italic><gray>amazing daily rewards
                    <!italic><dark_purple> 
                    <!italic><dark_purple><yellow>✦ Streak Multiplier: </yellow><white>{1}
                    <!italic><dark_purple> 
                    <!italic><red>Available in {2}
                    """.trimIndent()
            ),
            stopOnMatch = true,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )?:return
    Originals.getOrCreate(
        projectId = characterInfoProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """{re:(.+)} hours""".trimIndent()
            ),
            template = Project.Original.Template(
                """{1} hours""".trimIndent()
            ),
            stopOnMatch = true,
            parentId = dailyRewards.id,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = characterInfoProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """1 hour""".trimIndent()
            ),
            stopOnMatch = true,
            parentId = dailyRewards.id,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = characterInfoProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """{re:(.+)} minutes""".trimIndent()
            ),
            template = Project.Original.Template(
                """{1} minutes""".trimIndent()
            ),
            stopOnMatch = true,
            parentId = dailyRewards.id,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = characterInfoProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """1 minute""".trimIndent()
            ),
            stopOnMatch = true,
            parentId = dailyRewards.id,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )
    val info = Originals.getOrCreate(
        projectId = characterInfoProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><bold><white>{player}'s Info
                    <!italic><dark_purple>{re:(.+)}
                    <!italic><dark_purple> 
                    <!italic><dark_purple><gray>Total Lv: </gray><white>{re:(.+)}
                    <!italic><dark_purple><gray>Combat Lv: </gray><white>{re:(.+)}
                    <!italic><dark_purple><gray>Class: </gray><white>{re:(.+)}
                    <!italic><dark_purple><gray>Quests: </gray><white>{re:(.+)}
                    <!italic><dark_purple><gray>XP: </gray><white>{re:(.+)}
                    <!italic><dark_purple> 
                    <!italic><light_purple>Identifications: 
                    {re:([\s\S]+)}
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    <!italic><bold><white>{player}'s Info
                    <!italic><dark_purple>{1}
                    <!italic><dark_purple> 
                    <!italic><dark_purple><gray>Total Lv: </gray><white>{2}
                    <!italic><dark_purple><gray>Combat Lv: </gray><white>{3}
                    <!italic><dark_purple><gray>Class: </gray><white>{4}
                    <!italic><dark_purple><gray>Quests: </gray><white>{5}
                    <!italic><dark_purple><gray>XP: </gray><white>{6}
                    <!italic><dark_purple> 
                    <!italic><light_purple>Identifications: 
                    {7}
                    """.trimIndent()
            ),
            stopOnMatch = true,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )?:return
    val abilityTree = Originals.getOrCreate(
        projectId = characterInfoProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><bold><aqua>Ability Tree
                    <!italic><gray>Spend your Ability Points
                    <!italic><gray>and unlock new abilities!
                    <!italic><dark_purple> 
                    <!italic><dark_purple><dark_aqua>✦ Unused Points: </dark_aqua><white>{re:(.+)}
                    <!italic><dark_purple> 
                    <!italic><green>Click to Open
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    <!italic><bold><aqua>Ability Tree
                    <!italic><gray>Spend your Ability Points
                    <!italic><gray>and unlock new abilities!
                    <!italic><dark_purple> 
                    <!italic><dark_purple><dark_aqua>✦ Unused Points: </dark_aqua><white>{1}
                    <!italic><dark_purple> 
                    <!italic><green>Click to Open
                    """.trimIndent()
            ),
            stopOnMatch = true,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )?:return
    val cratesBombsCosmetics = Originals.getOrCreate(
        projectId = characterInfoProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><bold><yellow>Crates, Bombs & Cosmetics
                    <!italic><gray>Open your crates, equip
                    <!italic><gray>cosmetics, throw bombs,
                    <!italic><gray>and much more!
                    <!italic><dark_purple> 
                    <!italic><green>Click to Open
                    """.trimIndent()
            ),
            stopOnMatch = true,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )?:return
}