package com.wynnscribe.server.utils

import com.google.firebase.auth.FirebaseToken
import com.wynnscribe.models.Project
import com.wynnscribe.server.exceptions.UnauthorizedException
import io.ktor.server.auth.principal
import io.ktor.server.routing.RoutingCall

fun RoutingCall.authedOnly(): FirebaseToken {
    return this.principal<FirebaseToken>()?:throw UnauthorizedException()
}

val FirebaseToken.isAdmin: Boolean get() = this.claims["admin"] == true

fun RoutingCall.adminOnly(): FirebaseToken {
    val token = authedOnly()
    if(!token.isAdmin) throw UnauthorizedException()
    return token
}

fun FirebaseToken.userId(): Project.UserId {
    return Project.UserId(this.uid)
}