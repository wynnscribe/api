package com.wynnscribe.models

import kotlinx.serialization.Serializable

interface Export {
    val format: String

    @Serializable
    data class Create(
        override val format: String
    ): Export
}