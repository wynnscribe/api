package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

fun initializeGeneralItems() {
    val generalItemsProject = Projects.getOrCreate(
        Project.Create(
            name = "/General/Item",
            description = "",
            ))?:return

    Originals.getOrCreate(
        projectId = generalItemsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic>{re:(.+)}Unidentified {in:#wynnscribe.gear.names}
                    <!italic><gray>This item's powers have
                    <!italic><gray>been sealed. Take it to
                    <!italic><gray>an identifier to unlock
                    <!italic><gray>its potential.
                    <!italic><dark_purple> 
                    <!italic><green>Info:
                    <!italic><dark_purple><green>- </green><gray>Lv. Range: </gray><white>{re:(.+)}
                    <!italic><dark_purple><green>- </green><gray>Tier: {re:(.+)}
                    <!italic><dark_purple><green>- </green><gray>Type: </gray><white>{in:#wynnscribe.gear.names}
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    <!italic>{1}Unidentified {in:1}
                    <!italic><gray>This item's powers have
                    <!italic><gray>been sealed. Take it to
                    <!italic><gray>an identifier to unlock
                    <!italic><gray>its potential.
                    <!italic><dark_purple> 
                    <!italic><green>Info:
                    <!italic><dark_purple><green>- </green><gray>Lv. Range: </gray><white>{2}
                    <!italic><dark_purple><green>- </green><gray>Tier: {3}
                    <!italic><dark_purple><green>- </green><gray>Type: </gray><white>{in:2}
                    """.trimIndent()
            ),
            stopOnMatch = true,
            children = listOf(),
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.gear-box",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            ),
            type = Project.Original.Type.UserInterface
        )
    )

    // >>> ダンジョンの鍵 >>>
    val dungeonKey = Originals.getOrCreate(
        projectId = generalItemsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><gold>{re:(.+)} Key
                    <!italic><gray>Grants access to the
                    <!italic>{re:.+}<white>{re:(.+)}</white><gray> once
                    {re:([\s\S]+)}
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    <!italic><gold>{1} Key
                    <!italic><gray>Grants access to the
                    <!italic><dark_purple><white>{2}</white><gray> once
                    {3}
                    """.trimIndent()
            ),
            stopOnMatch = true,
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.dungeon-key",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            ),
            type = Project.Original.Type.GameItem,
        )
    )?:return
    Originals.getOrCreate(
        projectId = generalItemsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    Dungeon Info:
                    """.trimIndent()
            ),
            stopOnMatch = false,
            parentId = dungeonKey.id,
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = generalItemsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    Combat Lv:
                    """.trimIndent()
            ),
            stopOnMatch = false,
            parentId = dungeonKey.id,
            type = Project.Original.Type.GameItem
        )
    )
    Originals.getOrCreate(
        projectId = generalItemsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    Coordinates:
                    """.trimIndent()
            ),
            stopOnMatch = false,
            parentId = dungeonKey.id,
            type = Project.Original.Type.GameItem
        )
    )
    // <<< ダンジョン鍵 <<<

    // テレポートスクロール
    Originals.getOrCreate(
        projectId = generalItemsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><#8193ff>{re:(.+)} Teleportation Scroll <#f9e79e>[{re:(.+)}]
                    <!italic><gray>Warps to {re:(.+)}
                    <!italic><dark_purple> 
                    <!italic><white><font:space>󐀅</font><font:keybind></font> Right-Click to use
                    <!italic><dark_purple> 
                    <!italic><dark_gray>Charges are restored every {re:(.+)}
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    <!italic><#8193ff>{1} Teleportation Scroll <#f9e79e>[{2}]
                    <!italic><gray>Warps to {3}</font>
                    <!italic><dark_purple> 
                    <!italic><white><font:space>󐀅</font><font:keybind></font> Right-Click to use
                    <!italic><dark_purple> 
                    <!italic><dark_gray>Charges are restored every {4}
                    """.trimIndent()
            ),
            stopOnMatch = true,
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.teleport-scroll",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            ),
            type = Project.Original.Type.GameItem,
        )
    )?:return
    val emeraldPouch = Originals.getOrCreate(
        projectId = generalItemsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText(
                """
                    <!italic><white><green>Emerald Pouch</green><dark_green> [Tier {re:(.+)}]
                    {re:(.+)}
                    <!italic><dark_purple> 
                    {re:([\s\S]+)}
                    <!italic><dark_purple> 
                    <!italic><dark_purple><white>Right-Click </white><gray>to view contents
                    """.trimIndent()
            ),
            template = Project.Original.Template(
                """
                    <!italic><white><green>Emerald Pouch</green><dark_green> [Tier {1}]
                    {2}
                    <!italic><dark_purple> 
                    {3}
                    <!italic><dark_purple> 
                    <!italic><dark_purple><white>Right-Click </white><gray>to view contents
                    """.trimIndent()
            ),
            stopOnMatch = true,
            filter = Project.Filter.Create(
                Project.Filter.Content.Create(
                    content = "#wynnscribe.item.emerald-pouch",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            ),
            type = Project.Original.Type.GameItem,
        )
    )?:return
    Originals
        .getOrCreate(
        projectId = generalItemsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("<gray>- Converts up to </gray><white>{re:(.+)}"),
            template = Project.Original.Template("<gray>- Converts up to </gray><white>{1}"),
            parentId = emeraldPouch.id,
            stopOnMatch = false,
            type = Project.Original.Type.GameItem,
        )
    )
    Originals.getOrCreate(
        projectId = generalItemsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("No Auto-Conversions"),
            parentId = emeraldPouch.id,
            stopOnMatch = false,
            type = Project.Original.Type.GameItem,
        )
    )
    Originals.getOrCreate(
        projectId = generalItemsProject.id,
        original = Project.Original.Create(
            text = Project.Original.OriginalText("<gray>- {re:(.+)} Rows </gray><dark_gray>({re:(.+)} Total)"),
            template = Project.Original.Template("<gray>- {1} Rows </gray><dark_gray>({2} Total)"),
            parentId = emeraldPouch.id,
            stopOnMatch = false,
            type = Project.Original.Type.GameItem,
        )
    )
}