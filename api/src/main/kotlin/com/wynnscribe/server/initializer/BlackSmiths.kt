package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializeBlackSmiths() {
    val sellProject = Projects.getOrCreate(
        Project.Create(
            name = "/UserInterface/BlackSmith/Sell",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "\uDAFF\uDFF8\uE016",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )
        )
    ) ?: return
    Originals.getOrCreate(
        projectId = sellProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><bold><gold>Repair Items
                <!italic><gray>Repair Gathering Tools
                <!italic><gray>and Crafted Items!
                <!italic><dark_purple> 
                <!italic><white><font:keybind></font><green> Click to switch
            """.trimIndent()),
            stopOnMatch = true,
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = sellProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><bold><dark_gray>Empty Item Slot
                <!italic><dark_purple> 
                <!italic><dark_gray>Click on an item
                <!italic><dark_gray>to sell it
            """.trimIndent()),
            stopOnMatch = true,
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = sellProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><bold><gray>Add items to sell them
                <!italic><dark_gray>Click on items in your inventory
                <!italic><dark_gray>to add them to be sold
            """.trimIndent()),
            stopOnMatch = true,
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = sellProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><bold><yellow>You are selling
                <!italic><dark_purple> 
                {re:([\s\S]+)}
                <!italic><dark_purple> 
                <!italic><gray>Expected Price: {re:(.+)}
                <!italic><dark_purple> 
                <!italic><white><font:keybind></font><yellow> Click to confirm
            """.trimIndent()),
            template = Project.Original.Template("""
                <!italic><bold><yellow>You are selling
                <!italic><dark_purple> 
                {1}
                <!italic><dark_purple> 
                <!italic><gray>Expected Price: {2}
                <!italic><dark_purple> 
                <!italic><white><font:keybind></font><yellow> Click to confirm
            """.trimIndent()),
            stopOnMatch = true,
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = sellProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><bold><green>Confirm Selling
                <!italic><dark_gray>You are about to sell
                <!italic><dark_purple> 
                {re:([\s\S]+)}
                <!italic><dark_purple> 
                <!italic><gray>Expected Price: {re:(.+)}
                <!italic><dark_purple> 
                <!italic><white><font:keybind></font><green> Click again to confirm
            """.trimIndent()),
            template = Project.Original.Template("""
                <!italic><bold><green>Confirm Selling
                <!italic><dark_gray>You are about to sell
                <!italic><dark_purple> 
                {1}
                <!italic><dark_purple> 
                <!italic><gray>Expected Price: {2}
                <!italic><dark_purple> 
                <!italic><white><font:keybind></font><green> Click again to confirm
            """.trimIndent()),
            stopOnMatch = true,
            type = Project.Original.Type.UserInterface
        )
    )
    val repairProject = Projects.getOrCreate(
        Project.Create(
            name = "/UserInterface/BlackSmith/Repair",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "\uDAFF\uDFF8\uE017",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )
        )
    ) ?: return
    Originals.getOrCreate(
        projectId = repairProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><bold><green>Sell Items
                <!italic><gray>Sell weapons, accessories,
                <!italic><gray>armours and more!
                <!italic><dark_purple> 
                <!italic><white><font:keybind></font><green> Click to switch
            """.trimIndent()),
            stopOnMatch = true,
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = repairProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><bold><dark_gray>Empty Item Slot
                <!italic><dark_purple> 
                <!italic><dark_gray>You don't have any other
                <!italic><dark_gray>item to repair!
            """.trimIndent()),
            stopOnMatch = true,
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = repairProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><gold>{re:(.+)}
                <!italic><dark_gray>[{re:(.+)} Durability]
                <!italic><dark_purple> 
                <!italic><yellow>Repair Cost
                {re:([\s\S]+)}
                <!italic><dark_purple> 
                <!italic><white><font:keybind></font><green> Click to repair
            """.trimIndent()),
            template = Project.Original.Template("""
                <!italic><gold>{1}
                <!italic><dark_gray>[{2} Durability]
                <!italic><dark_purple> 
                <!italic><yellow>Repair Cost
                {3}
                <!italic><dark_purple> 
                <!italic><white><font:keybind></font><green> Click to repair
            """.trimIndent()),
            stopOnMatch = true,
            type = Project.Original.Type.UserInterface
        )
    )
}