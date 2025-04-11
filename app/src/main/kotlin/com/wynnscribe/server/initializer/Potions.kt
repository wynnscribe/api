package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializePotions() {
    val potionsProject = Projects.getOrCreate(
        Project.Create(
            name = "/Item/Potions",
            description = "",
            filter = Project.Filter.Create(
                null,
                null,
                null
            )))?:return
    Originals.getOrCreate(
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><white><red>[+{re:(.+)} ❤] </red><light_purple>Potions of Healing </light_purple><dark_red>[{re:(.+)}]
                <!italic><dark_purple><white>Drink</white><gray> to heal over {re:(\d+)}s
                <!italic><dark_purple><white>Right-Click</white><gray> to delete potions
                <!italic><dark_purple> 
                {re:([\s\S]+)}
            """.trimIndent()),
            template = Project.Original.Template("""
                <!italic><white><red>[+{1} ❤] </red><light_purple>Potions of Healing </light_purple><dark_red>[{2}]
                <!italic><dark_purple><white>Drink</white><gray> to heal over {3}s
                <!italic><dark_purple><white>Right-Click</white><gray> to delete potions
                <!italic><dark_purple> 
                {4}
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.multi-health-potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><white><red>Potion of Healing</red><dark_red> [{re:(\d+/\d+)}]
            """.trimIndent()),
            template = Project.Original.Template("""
                <!italic><white><red>Potion of Healing</red><dark_red> [{1}]
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><white><aqua>Potion of Mana</aqua><dark_aqua> [{re:(\d+/\d+)}]
            """.trimIndent()),
            template = Project.Original.Template("""
                <!italic><white><aqua>Potion of Mana</aqua><dark_aqua> [{1}]
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><white><green>Potion of </green>{re:(.+)?}{in:#wynnscribe.status}{re:(.+)?}<green> [{re:(\d+/\d+)}]
            """.trimIndent()),
            template = Project.Original.Template("""
                <!italic><white><green>Potion of </green>{1}{in:1}{2}<green> [{3}]
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><gold>Potion of Wisdom
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><white>Nemract Whiskey
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Effect:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Duration:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Mana:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Heal:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Combat Lv. Min:
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><dark_gray>Drinking this potion will
                <!italic><dark_gray>boost your ✹ Defence by
                <!italic><dark_gray>{re:(\d+)} and boost your Health
                <!italic><dark_gray>by +{re:(\d+)} for {re:(\d+)} seconds
            """.trimIndent()),
            template = Project.Original.Template("""
                <!italic><dark_gray>Drinking this potion will
                <!italic><dark_gray>boost your ✹ Defence by
                <!italic><dark_gray>{1} and boost your Health
                <!italic><dark_gray>by +{2} for {3} seconds
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><dark_gray>Drinking this potion will
                <!italic><dark_gray>boost your ✦ Dexterity by
                <!italic><dark_gray>{re:(\d+)} and boost your Main Attack
                <!italic><dark_gray>Damage by +{re:(\d+)} for {re:(\d+)} seconds
            """.trimIndent()),
            template = Project.Original.Template("""
                <!italic><dark_gray>Drinking this potion will
                <!italic><dark_gray>boost your ✦ Dexterity by
                <!italic><dark_gray>{1} and boost your Main Attack
                <!italic><dark_gray>Damage by +{2} for {3} seconds
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><dark_gray>Drinking this potion will
                <!italic><dark_gray>boost your ❉ Intelligence
                <!italic><dark_gray>by {re:(\d+)} and boost your Spell
                <!italic><dark_gray>Damage by +{re:(\d+)}% for {re:(\d+)} seconds
            """.trimIndent()),
            template = Project.Original.Template("""
                <!italic><dark_gray>Drinking this potion will
                <!italic><dark_gray>boost your ❉ Intelligence
                <!italic><dark_gray>by {1} and boost your Spell
                <!italic><dark_gray>Damage by +{2}% for {3} seconds
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><dark_gray>Drinking this potion will
                <!italic><dark_gray>boost your ❋ Agility by
                <!italic><dark_gray>{re:(\d+)} and boost your Walk Speed
                <!italic><dark_gray>by +{re:(\d+)}% for {re:(\d+)} seconds
            """.trimIndent()),
            template = Project.Original.Template("""
                <!italic><dark_gray>Drinking this potion will
                <!italic><dark_gray>boost your ❋ Agility by
                <!italic><dark_gray>{1} and boost your Walk Speed
                <!italic><dark_gray>by +{2}% for {3} seconds
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><dark_gray>Drinking this potion will
                <!italic><dark_gray>boost your ✤ Strength by
                <!italic><dark_gray>{re:(\d+)} and boost your Main Attack
                <!italic><dark_gray>Damage by +{re:(\d+)}% for {re:(\d+)} seconds
            """.trimIndent()),
            template = Project.Original.Template("""
                <!italic><dark_gray>Drinking this potion will
                <!italic><dark_gray>boost your ✤ Strength by
                <!italic><dark_gray>{1} and boost your Main Attack
                <!italic><dark_gray>Damage by +{2}% for {3} seconds
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><dark_gray>Drinking this potion will
                <!italic><dark_gray>boost your XP bonus by {re:(\d+)}%
                <!italic><dark_gray>for {re:(\d+)} seconds.
            """.trimIndent()),
            template = Project.Original.Template("""
                <!italic><dark_gray>Drinking this potion will
                <!italic><dark_gray>boost your XP bonus by {1}%
                <!italic><dark_gray>for {2} seconds.
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><dark_gray>Drinking this potion will
                <!italic><dark_gray>regenerate your health over
                <!italic><dark_gray>{re:(\d+)} seconds.
            """.trimIndent()),
            template = Project.Original.Template("""
                <!italic><dark_gray>Drinking this potion will
                <!italic><dark_gray>regenerate your health over
                <!italic><dark_gray>{1} seconds.
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><dark_gray>Drinking this potion will
                <!italic><dark_gray>regenerate your mana over
                <!italic><dark_gray>{re:(\d+)} seconds.
            """.trimIndent()),
            template = Project.Original.Template("""
                <!italic><dark_gray>Drinking this potion will
                <!italic><dark_gray>regenerate your mana over
                <!italic><dark_gray>{1} seconds.
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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
        projectId = potionsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                {re:(\d+%*)} {in:#wynnscribe.stats}
            """.trimIndent()),
            template = Project.Original.Template("""
                {1} {in:1}
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.potion",
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