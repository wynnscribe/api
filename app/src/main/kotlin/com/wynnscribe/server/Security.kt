package com.wynnscribe.server

import com.wynnscribe.server.libs.FirebaseAdmin
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureSecurity() {
    install(CORS) {
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Accept)
        allowHeader(HttpHeaders.UserAgent)
        allowHeader(HttpHeaders.Referrer)
        allowHeader(HttpHeaders.Origin)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Patch)
        allowCredentials = true
        anyHost() // TODO
    }

    install(Authentication) {
        bearer("auth-bearer") {
            authenticate { credential ->
                FirebaseAdmin.Auth().verifyToken(credential.token, false).getOrNull()
            }
        }
    }
}