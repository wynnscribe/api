package com.wynnscribe.server.routes.api.v1

import com.wynnscribe.models.LoginBody
import com.wynnscribe.server.UserSession
import com.wynnscribe.server.exceptions.UnauthorizedException
import com.wynnscribe.server.libs.FirebaseAdmin
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.sessions.clear
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set

fun Route.authentication() {
    post("login") {
        val body = call.receive<LoginBody>()
        val sessionCookie = FirebaseAdmin.Auth().createSessionCookie(body.idToken).getOrNull()
        if(sessionCookie == null) throw UnauthorizedException()
        call.sessions.set(UserSession(sessionCookie))
        call.respond(HttpStatusCode.OK)
    }
    get("logout") {
        val session = call.sessions.get<UserSession>()
        if(session != null) call.sessions.clear<UserSession>()
        call.respond(HttpStatusCode.OK)
    }
}