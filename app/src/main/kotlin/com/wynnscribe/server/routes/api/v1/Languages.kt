package com.wynnscribe.server.routes.api.v1

import com.ucasoft.ktor.simpleCache.cacheOutput
import com.wynnscribe.models.Language
import com.wynnscribe.server.databases.Languages
import com.wynnscribe.server.utils.adminOnly
import com.wynnscribe.server.utils.authedOnly
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.time.Duration.Companion.hours

fun Route.publicLanguages() {
    cacheOutput(1.hours) {
        get("languages") {
            val returned = transaction { Languages.all() }
            call.response.headers.append(HttpHeaders.ContentType, "application/json")
            call.respond(returned)
        }
    }
}

fun Route.languages() {
    route("languages") {
        post {
            call.adminOnly()
            val body = call.receive<Language.Create>()
            val created = transaction { Languages.create(body) }
            if(created == null) {
                call.respond(HttpStatusCode.BadRequest)
            } else {
                call.respond(HttpStatusCode.Created, created)
            }
        }

        route("{languageId}") {

            fun RoutingCall.languageId(): Language.LanguageId = Language.LanguageId(this.pathParameters["languageId"]?:throw BadRequestException("Invalid language id."))

            get {
                call.authedOnly()
                val languageId = call.languageId()
                val returned = transaction { Languages.get(languageId) }?:throw NotFoundException("Language not found.")
                call.respond(returned)
            }

            delete {
                call.adminOnly()
                val languageId = call.languageId()
                val has = transaction { Languages.has(languageId) }
                if(has) {
                    transaction { Languages.delete(languageId) }
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}