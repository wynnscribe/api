package com.wynnscribe.server.routes.api.v1

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Translations
import com.wynnscribe.server.exceptions.UnauthorizedException
import com.wynnscribe.server.utils.adminOnly
import com.wynnscribe.server.utils.authedOnly
import com.wynnscribe.server.utils.isAdmin
import com.wynnscribe.server.utils.isNeedReview
import com.wynnscribe.server.utils.userId
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.jetbrains.exposed.sql.transactions.transaction

fun RoutingCall.translationId() = Project.Original.Translation.TranslationId(this.pathParameters["translationId"]?.toLongOrNull()?:throw BadRequestException("Invalid translation id."))

fun Route.translations() {
    route("translations") {

        get("details") {
            call.authedOnly()
            val originalId = call.originalId()
            val languageId = call.languageId()
            val details = transaction { Originals.translationDetails(originalId, languageId) }
            call.respond(details)
        }

        post {
            val user = call.authedOnly()
            val originalId = call.originalId()
            val lang = call.languageId()
            val body = call.receive<Project.Original.Translation.Create>()
            val has = transaction { Originals.has(originalId) }
            if(has) {
                throw BadRequestException("Original not found")
            }
            if(isNeedReview(body.text.value)) {
                body.status = Project.Original.Translation.Status.NeedsReview
            }
            val inserted = transaction {
                Translations.createOrUpdate(body, user.userId(), lang, originalId)
            }
            if(inserted == null) {
                call.respond(HttpStatusCode.BadRequest)
            } else {
                call.respond(inserted)
            }
        }

        route("{translationId}") {

            votes()

            patch("status") {
                call.adminOnly()
                val translationId = call.translationId()
                val body = call.receive<Project.Original.Translation.UpdateStatus>()
                val updated = transaction { Translations.updateStatus(translationId, body.status) }
                if(updated == null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    call.respond(updated)
                }
            }

            delete {
                val user = call.authedOnly()
                val translationId = call.translationId()
                if(user.isAdmin) {
                    transaction { Translations.delete(translationId) }
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    val translation = transaction { Translations.get(translationId) }
                    if(translation == null) { throw BadRequestException("Invalid transaction id.") }
                    if(translation.userId != user.userId()) { throw UnauthorizedException() }
                    val successful = transaction { Translations.delete(translationId, user.userId()) }
                    if(!successful) {
                        call.respond(HttpStatusCode.BadRequest)
                    } else {
                        call.respond(HttpStatusCode.NoContent)
                    }
                }
            }
        }
    }
}