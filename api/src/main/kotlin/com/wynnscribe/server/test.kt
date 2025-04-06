package com.wynnscribe.server

import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode

fun main() {
    fun named(color: String): String? {
        return when (color) {
            "#000000" -> "§0"
            "#0000AA" -> "§1"
            "#00AA00" -> "§2"
            "#00AAAA" -> "§3"
            "#AA0000" -> "§4"
            "#AA00AA" -> "§5"
            "#FFAA00" -> "§6"
            "#AAAAAA" -> "§7"
            "#555555" -> "§8"
            "#5555FF" -> "§9"
            "#55FF55" -> "§a"
            "#55FFFF" -> "§b"
            "#FF5555" -> "§c"
            "#FF55FF" -> "§d"
            "#FFFF55" -> "§e"
            "#FFFFFF" -> "§f"
            else -> null
        }
    }

    fun parseChild(parent: Node): List<Node> {
        val nodes = mutableListOf<Node>()
        parent.childNodesCopy().forEachIndexed { index, node ->
            if (node is Element) {
                val style = node.attr("style")
                when (node.attr("class")) {
                    "font-ascii" -> {
                        if (style.startsWith("color:")) {
                            val named = named(style.removePrefix("color:"))
                            if (named != null) {
                                nodes.add(TextNode(named))
                            }
                            nodes.addAll(parseChild(node))
                        } else if (style.startsWith("text-decoration:")) {
                            when (style.removePrefix("text-decoration: ")) {
                                "underline" -> {
                                    nodes.add(TextNode("<underlined>"))
                                    nodes.addAll(parseChild(node))
                                    nodes.add(TextNode("</underlined>"))
                                }

                                "line-through" -> {
                                    nodes.add(TextNode("<strikethrough>"))
                                    nodes.addAll(parseChild(node))
                                    nodes.add(TextNode("</strikethrough>"))
                                }
                            }
                        } else {
                            nodes.add(node)
                        }
                    }
                    "font-common" -> {
                        nodes.add(TextNode("<font:common>"))
                        nodes.addAll(parseChild(node))
                        nodes.add(TextNode("</font>"))

                    }
                    else -> {
                        nodes.add(node)
                    }
                }
            } else {
                // 文字だった場合は普通に追加
                nodes.add(node)
            }
        }
        return nodes
    }

    val list = listOf(
        "",
        "<span class='font-ascii' style='color:#FFFFFF'>Double<span class='font-ascii' style='color:#AAAAAA'> your </span><span class='font-ascii' style='text-decoration: underline'>Main Attack</span><span class='font-ascii' style='color:#AAAAAA'>'s beam speed and</span></span>",
        "<span class='font-ascii' style='color:#AAAAAA'>increase your damage when using a relik.</span>",
        "",
        "<span class='font-ascii' style='color:#FF5555'><span class='font-common'></span> <span class='font-ascii' style='color:#AAAAAA'>Main Attack Damage: </span><span class='font-ascii' style='color:#FFFFFF'>+5%</span></span></span>",
        "",
        "<span class='font-ascii' style='color:#AAAAAA'>Ability Points: <span class='font-ascii' style='color:#FFFFFF'>1</span></span>"
    )

    val parsed = Jsoup.parse(list.joinToString("\n") { if (it.isEmpty()) "&nbsp;" else it })
    parsed.outputSettings(Document.OutputSettings().prettyPrint(false))
    println(
        parseChild(parsed.body()).joinToString("").replace("&lt;", "<").replace("&gt;", ">").split("\n")
            .map { if(it.isEmpty()) " " else it }
            .map { "<!italic><dark_purple>§7$it" }.joinToString("\n") {
                MiniMessage.miniMessage().serialize(
                    MiniMessage.miniMessage().deserialize(
                        MiniMessage.miniMessage().serialize(
                            LegacyComponentSerializer.legacySection().deserialize(
                                it
                            )
                        ).replace("\\<", "<")
                    )
                )
            }.replace("&nbsp;", " ")
    )
}