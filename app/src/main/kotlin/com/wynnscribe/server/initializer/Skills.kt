package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializeSkills() {
    val skillsProject = Projects.getOrCreate(
        Project.Create(
            name = "/UserInterface/CharacterInfo/Skills",
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

    val skillItemBase = Originals.getOrCreate(
        projectId = skillsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><white><light_purple>Upgrade your {re:(.+)}<light_purple> skill
                    <!italic><gray>                                          
                    <!italic><dark_purple><font:space><gray>󐀔</gray></font><bold><gray>Now</gray></bold><font:space>󐀡</font>       <font:space>󐀞</font><bold><gold>Next
                    {re:([\s\S]+)}
                    <!italic><dark_purple> 
                    {re:([\s\S]+)}
                    <!italic><dark_purple> 
                    {re:([\s\S]+)}
                    """.trimIndent()
            ),
            stopOnMatch = true,
            template = Project.Original.Template(
                """
                    <!italic><white><light_purple>Upgrade your {1}<light_purple> skill
                    <!italic><gray>                                          
                    <!italic><dark_purple><font:space><gray>󐀔</gray></font><bold><gray>Now</gray></bold><font:space>󐀡</font>       <font:space>󐀞</font><bold><gold>Next
                    {2}
                    <!italic><dark_purple> 
                    {3}
                    <!italic><dark_purple> 
                    {4}
                    """.trimIndent()
            ),
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )?:return

    Originals.getOrCreate(
        projectId = skillsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><gray>Each point in this skill will
                    <!italic><dark_purple><light_purple>increase </light_purple><gray>any damage you deal,
                    <!italic><dark_purple><gray>and increase the </gray><dark_green>✤ Earth
                    <!italic><gray>damage you may inflict
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            parentId = skillItemBase.id,
            type = Project.Original.Type.UserInterface
        )
    )

    Originals.getOrCreate(
        projectId = skillsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><gray>Each point in this skill will
                    <!italic><dark_purple><light_purple>increase </light_purple><gray>the chance to do a
                    <!italic><gray>critical hit (doubling damage),
                    <!italic><dark_purple><gray>and increase the </gray><yellow>✦ Thunder
                    <!italic><gray>damage you may inflict
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            parentId = skillItemBase.id,
            type = Project.Original.Type.UserInterface
        )
    )

    Originals.getOrCreate(
        projectId = skillsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><gray>Each point in this skill will
                    <!italic><dark_purple><light_purple>increase </light_purple><gray>your maximum mana,
                    <!italic><dark_purple><gray>the </gray><aqua>❉ Water</aqua><gray> damage you may
                    <!italic><dark_purple><gray>inflict, and </gray><light_purple>reduce</light_purple><gray> spell costs
                    <!italic><dark_purple> 
                    <!italic><dark_purple><dark_aqua>✺ </dark_aqua><aqua>Spell Cost Reduction: </aqua><white>{re:(.+)}%
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            template = Project.Original.Template(
                """
                    <!italic><gray>Each point in this skill will
                    <!italic><dark_purple><light_purple>increase </light_purple><gray>your maximum mana,
                    <!italic><dark_purple><gray>the </gray><aqua>❉ Water</aqua><gray> damage you may
                    <!italic><dark_purple><gray>inflict, and </gray><light_purple>reduce</light_purple><gray> spell costs
                    <!italic><dark_purple> 
                    <!italic><dark_purple><dark_aqua>✺ </dark_aqua><aqua>Spell Cost Reduction: </aqua><white>{1}%
                    """.trimIndent()
            ),
            parentId = skillItemBase.id,
            type = Project.Original.Type.UserInterface
        )
    )

    Originals.getOrCreate(
        projectId = skillsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><gray>Each point in this skill will
                    <!italic><dark_purple><light_purple>reduce </light_purple><gray>any damage you take,
                    <!italic><dark_purple><gray>and increase the </gray><red>✹ Fire</red><gray> damage
                    <!italic><gray>you may inflict
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            parentId = skillItemBase.id,
            type = Project.Original.Type.UserInterface
        )
    )

    Originals.getOrCreate(
        projectId = skillsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><gray>Each point in this skill will
                    <!italic><dark_purple><light_purple>increase </light_purple><gray>the chance to dodge
                    <!italic><gray>attacks (90% damage reduction), 
                    <!italic><dark_purple><gray>and increase the </gray><white>❋ Air</white><gray> damage
                    <!italic><gray>you may inflict
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            parentId = skillItemBase.id,
            type = Project.Original.Type.UserInterface
        )
    )

    Originals.getOrCreate(
        projectId = skillsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><green>Left-Click to add 1 point
                    <!italic><red>Right-Click to remove 1 point
                    <!italic><dark_gray>Hold Shift to modify by 5
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            parentId = skillItemBase.id,
            type = Project.Original.Type.UserInterface
        )
    )

    Originals.getOrCreate(
        projectId = skillsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><green>Left-Click to add 1 point
                    <!italic><dark_gray>Hold Shift to modify by 5
                    <!italic><red>You need to be inside a town
                    <!italic><red>to remove skill points
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            parentId = skillItemBase.id,
            type = Project.Original.Type.UserInterface
        )
    )

}