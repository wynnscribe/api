package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializeItemIdentifier() {
    val sellProject = Projects.getOrCreate(
        Project.Create(
            name = "/UserInterface/ItemIdentifier/Identifier",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "\uDAFF\uDFF8\uE018",
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
                <!italic><bold><aqua>Corkian Augments
                <!italic><dark_purple> 
                <!italic><gray>Corkian augments provides
                <!italic><gray>augments while identifying
                <!italic><gray>a single item
                <!italic><dark_purple> 
                <!italic><white><font:keybind></font> <green>Click to Open
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
                <!italic><dark_gray>to be identified
            """.trimIndent()),
            stopOnMatch = true,
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = sellProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><bold><gray>Add items to identify
                <!italic><dark_gray>Click on items in your inventory
                <!italic><dark_gray>to add them to be identified
            """.trimIndent()),
            stopOnMatch = true,
            type = Project.Original.Type.UserInterface
        )
    )

    Originals.getOrCreate(
        projectId = sellProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><yellow>Please wait...
            """.trimIndent()),
            stopOnMatch = true,
            type = Project.Original.Type.UserInterface
        )
    )

    Originals.getOrCreate(
        projectId = sellProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><bold><green>Withdraw Items
                <!italic><dark_gray>Click to remove all items
            """.trimIndent()),
            stopOnMatch = true,
            type = Project.Original.Type.UserInterface
        )
    )


    Originals.getOrCreate(
        projectId = sellProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><bold><yellow>You are identifying
                <!italic><dark_purple> 
                {re:([\s\S]+)}
                <!italic><dark_purple> 
                <!italic><dark_purple><gray>Price: </gray>{re:(.+)}
                <!italic><dark_purple> 
                <!italic><white><font:keybind></font><yellow> Click to confirm
            """.trimIndent()),
            stopOnMatch = false,
            template = Project.Original.Template("""
                <!italic><bold><yellow>You are identifying
                <!italic><dark_purple> 
                {1}
                <!italic><dark_purple> 
                <!italic><dark_purple><gray>Price: </gray>{2}
                <!italic><dark_purple> 
                <!italic><white><font:keybind></font><yellow> Click to confirm
            """.trimIndent()),
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )

    val argumentsProject = Projects.getOrCreate(
        Project.Create(
            name = "/UserInterface/ItemIdentifier/Arguments",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "\uDAFF\uDFF8\uE019",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )
        )
    ) ?: return
    Originals.getOrCreate(
        projectId = argumentsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><gray>Back to Identifier
            """.trimIndent()),
            stopOnMatch = true,
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = argumentsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><bold><dark_gray>Empty Item Slot
                <!italic><dark_purple> 
                <!italic><dark_gray>Click on an item
                <!italic><dark_gray>to be identified
            """.trimIndent()),
            stopOnMatch = true,
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = argumentsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><bold><dark_gray>Empty Augment Slot
                <!italic><dark_purple> 
                <!italic><dark_gray>Add an item to apply
                <!italic><dark_gray>augments
            """.trimIndent()),
            stopOnMatch = true,
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = argumentsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><bold><gray>Waiting for an item
                <!italic><dark_gray>Click on an item in your inventory
                <!italic><dark_gray>to be identified
            """.trimIndent()),
            stopOnMatch = true,
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = argumentsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><bold><gray>Waiting for an Augment
                <!italic><dark_gray>Click on a corkian augment in your
                <!italic><dark_gray>inventory to add it
            """.trimIndent()),
            stopOnMatch = true,
            type = Project.Original.Type.UserInterface
        )
    )
}