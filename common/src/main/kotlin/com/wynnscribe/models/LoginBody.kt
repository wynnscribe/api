package com.wynnscribe.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(
    val idToken: String
)
