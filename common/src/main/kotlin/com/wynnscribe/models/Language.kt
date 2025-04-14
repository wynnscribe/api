package com.wynnscribe.models

import kotlinx.serialization.Serializable

interface Language {
    val id: LanguageId

    val name: String

    val englishName: String

    val emoji: String

    val color: String

    @Serializable
    @JvmInline
    value class LanguageId(val value: String)

    @Serializable
    data class Impl(
        override val id: Language.LanguageId,
        override val name: String,
        override val englishName: String,
        override val emoji: String,
        override val color: String
    ): Language

    @Serializable
    data class Create(
        override val id: LanguageId,
        override val name: String,
        override val englishName: String,
        override val emoji: String,
        override val color: String
    ): Language
}