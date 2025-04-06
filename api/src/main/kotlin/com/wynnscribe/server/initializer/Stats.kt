package com.wynnscribe.server.initializer

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Projects

val stats = mapOf(
    "Health" to "Health",
    "Health Regen" to "Health Regen",
    "Healing Efficiency" to "Healing Efficiency",
    "Life Steal" to "Life Steal",
    "Mana Regen" to "Mana Regen",
    "Mana Steal" to "Mana Steal",
    "Max Mana" to "Max Mana",
    "Walk Speed" to "Walk Speed",
    "Sprint" to "Sprint",
    "Sprint Regen" to "Sprint Regen",
    "Jump Height" to "Jump Height",
    "Attack Speed" to "Attack Speed",
    "Main Attack Range" to "Main Attack Range",
    "Reflection" to "Reflection",
    "Thorns" to "Thorns",
    "Exploding" to "Exploding",
    "Poison" to "Poison",
    "Knockback" to "Knockback",
    "Slow Enemy" to "Slow Enemy",
    "Weaken Enemy" to "Weaken Enemy",
    "Stealing" to "Stealing",
    "XP Bonus" to "XP Bonus",
    "Loot Bonus" to "Loot Bonus",
    "Loot Quality" to "Loot Quality",
    "Gather XP Bonus" to "Gather XP Bonus",
    "Gather Speed" to "Gather Speed",
    "Slaying XP" to "Slaying XP",
    "Gathering XP" to "Gathering XP",
    "Dungeon XP" to "Dungeon XP",
    "{in:#wynnscribe.damages} Damage" to "{in:1} Damage",
    "{in:#wynnscribe.damages} Defence" to "{in:1} Defence"
)

fun initializeStats() {
    val statsProject = Projects.getOrCreate(
        Project.Create(
            name = "/Stats",
            description = "",
            filter = Project.Filter.Create(
                type = Project.Filter.Content.Create(
                    content = "#wynnscribe.stats",
                    withColors = false,
                    fullMatch = true
                ),
                null,
                null
            )))?:return
    stats.forEach { (stat, template) ->
        Originals.getOrCreate(
            projectId = statsProject.id,
            original = Project.Original.Create(
                text = Project.Original.OriginalText(stat),
                stopOnMatch = true,
                template = Project.Original.Template(template),
                children = listOf(),
                type = Project.Original.Type.Stat
            )
        )
    }
}