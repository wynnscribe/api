package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializeContentBook() {
    val contentBookProject = Projects.getOrCreate(
        Project.Create(
            name = "/Item/ContentBook",
            description = "",
            filter = Project.Filter.Create(
                null,
                null,
                null
            )))?:return
    val contentBook = Originals.getOrCreate(
        projectId = contentBookProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                Content Book
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            filter = Project.Filter.Create(
                null,
                Project.Filter.Content.Create(
                    content = "Content Book",
                    withColors = false,
                    fullMatch = true
                ),
                null
            ),
            type = Project.Original.Type.GameItem
        )
    )?:return
    
    Originals.getOrCreate(
        projectId = contentBookProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("""
                <!italic><gray>Hold and Right-Click to see content
                <!italic><dark_purple> 
                <!italic><dark_purple>Quests: <light_purple>{re:(\d+)}/{re:(\d+)} <dark_purple>[{re:(\d+)}%]
                <!italic><dark_purple> 
                <!italic><dark_purple>Discoveries:
                <!italic><dark_purple>- <gray>Territorial: <white>{re:(\d+)}/{re:(\d+)} <dark_purple>[{re:(\d+)}%]
                <!italic><dark_purple>- <gold>World: <yellow>{re:(\d+)}/{re:(\d+)} <dark_purple>[{re:(\d+)}%]
                <!italic><dark_purple>- <dark_aqua>Secret: <aqua>{re:(\d+)}/{re:(\d+)} <dark_purple>[{re:(\d+)}%]
            """.trimIndent()),
            template = Project.Original.Template("""
                <!italic><gray>Hold and Right-Click to see content
                <!italic><dark_purple> 
                <!italic><dark_purple>Quests: <light_purple>{1}/{2} <dark_purple>[{3}%]
                <!italic><dark_purple> 
                <!italic><dark_purple>Discoveries:
                <!italic><dark_purple>- <gray>Territorial: <white>{4}/{5} <dark_purple>[{6}%]
                <!italic><dark_purple>- <gold>World: <yellow>{7}/{8} <dark_purple>[{9}%]
                <!italic><dark_purple>- <dark_aqua>Secret: <aqua>{10}/{11} <dark_purple>[{12}%]
            """.trimIndent()),
            stopOnMatch = false,
            children = listOf(),
            parentId = contentBook.id,
            type = Project.Original.Type.GameItem
        )
    )?:return
    
}
