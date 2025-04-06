package com.wynnscribe.server

import com.wynnscribe.server.libs.FirebaseAdmin
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.bearer
import io.ktor.server.auth.session
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.response.respond
import io.ktor.server.sessions.*
import io.ktor.util.hex
import kotlinx.serialization.Serializable
import java.io.File
import kotlin.time.Duration.Companion.days

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

    install(Sessions) {
        val secretSignKey = hex(System.getenv("AUTH_COOKIE_SECRET"))
        cookie<UserSession>("session", directorySessionStorage(File(".sessions"))) {
            cookie.httpOnly = true
            cookie.maxAge = 5.days
            cookie.path = "/"
            cookie.sameSite = SameSite.Lax
            transform(SessionTransportTransformerMessageAuthentication(secretSignKey))
        }
    }

    install(Authentication) {
        bearer("auth-bearer") {
            authenticate { credential ->
                FirebaseAdmin.Auth().verifyToken(credential.token, false).getOrNull()
            }
        }
    }
}
@Serializable
data class UserSession(val token: String)
