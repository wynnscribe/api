package com.wynnscribe

import com.wynnscribe.models.Project
import com.wynnscribe.models.Project.Original.Json.Regexes.CompiledPlaceholder
import com.wynnscribe.utils.escapeGroupingRegex
import com.wynnscribe.utils.escapeRegex

interface Placeholder<T> {
    val tag: String

    fun placeholder(text: String): String {
        var num = 1
        return Regex("\\{${tag}(:.+?)?}").replace(text) { "{${tag}:${num++}}" }
    }

    /**
     * パースされたtagをHolderに変換します。
     */
    fun holder(tag: ParsedTag, original: Project.Original, projects: List<Project.Json>): CompiledPlaceholder.Holder<T>

    /**
     * このプレースホルダの正規表現をコンパイル(生成)します。
     */
    fun compile(holders: List<CompiledPlaceholder.Holder<*>>, original: Project.Original.Json): CompiledPlaceholder<T> {
        if("{${tag}" !in original.text.value) { return CompiledPlaceholder(null, emptyList()) }
        val myHolders = mutableListOf<CompiledPlaceholder.Holder<T>>()
        val patternStr = buildString {
            Placeholder.TAG_PATTERN.split(original.text.value).forEachIndexed { index, str ->
                append(escapeRegex(str))
                val holder = holders.getOrNull(index)
                val pattern = holder?.pattern
                if(holder != null && pattern != null) {
                    if(this@Placeholder != holder.type) {
                        // タグが違う場合は内部のグループをエスケープする
                        append(escapeGroupingRegex(pattern))
                    } else {
                        myHolders.add(holder as CompiledPlaceholder.Holder<T>)
                        append(pattern)
                    }
                }
            }
        }
        return CompiledPlaceholder(patternStr.toRegex(), myHolders)
    }

    fun on(translation: String, originalText: String, original: Project.Original.Json, projects: List<Project.Json>): String

    class ParsedTag(val key: String, val value: String?) {
        constructor(list: List<String>): this(list[0], list.getOrNull(1))
    }

    companion object {
        val TAG_PATTERN = Regex("(?<!\\\\)\\{((?:\\\\.|[^\\\\{}])*)}")
    }
}