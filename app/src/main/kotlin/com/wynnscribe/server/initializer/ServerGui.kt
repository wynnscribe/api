package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializeServerGui() {
    val serverListProject = Projects.getOrCreate(
        Project.Create(
            name = "/UserInterface/Server/List",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.gui.server",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )
        )
    ) ?: return
    Originals.getOrCreate(
        projectId = serverListProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    {re:(<.+?>)}{re:(.+)} | World {re:(.+)}
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    {1}{2} | World {3}
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = serverListProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    {re:(.+/.+)} Players Online
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    {1} Players Online
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = serverListProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <yellow> CHAMPION </yellow><gold>Slots
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = serverListProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    ({re:(.+)} Players Queued)
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    ({1} Players Queued)
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = serverListProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    Click to join
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = serverListProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    Recommended
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = serverListProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    {re:(.+)}% Lag
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    {1}% Lag
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = serverListProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    Uptime:
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = serverListProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    Will restart soon!
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )

    val serverPageProject = Projects.getOrCreate(
        Project.Create(
            name = "/UserInterface/Server/Button",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "Wynncraft Servers",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )
        )
    ) ?: return
    Originals.getOrCreate(
        projectId = serverPageProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><bold><gold>Server Region
                    <!italic><dark_purple> 
                    <!italic><gray>Filters the world list to show
                    <!italic><gray>only those in the selected region
                    <!italic><dark_purple> 
                    <!italic><red>* <dark_gray>Recommended Region
                    <!italic><dark_purple> 
                    {re:([\s\S]+)}
                    <!italic><dark_purple> 
                    <!italic><green>Left-Click to go forward
                    <!italic><green>Right-Click to go backward
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    <!italic><bold><gold>Server Region
                    <!italic><dark_purple> 
                    <!italic><gray>Filters the world list to show
                    <!italic><gray>only those in the selected region
                    <!italic><dark_purple> 
                    <!italic><red>* <dark_gray>Recommended Region
                    <!italic><dark_purple> 
                    {1}
                    <!italic><dark_purple> 
                    <!italic><green>Left-Click to go forward
                    <!italic><green>Right-Click to go backward
                    """.trimIndent()
            ),
            stopOnMatch = true,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = serverPageProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    Join a new Lobby
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )
    Originals.getOrCreate(
        projectId = serverPageProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    Back to Server List
                    """.trimIndent()
            ),
            stopOnMatch = false,
            children = listOf(),
            type = Project.Original.Type.UserInterface
        )
    )
}