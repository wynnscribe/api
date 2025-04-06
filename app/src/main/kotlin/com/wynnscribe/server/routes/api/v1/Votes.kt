package com.wynnscribe.server.routes.api.v1

import com.wynnscribe.models.Project.Original.Translation.Vote
import com.wynnscribe.server.databases.Translations
import com.wynnscribe.server.databases.Votes
import com.wynnscribe.server.utils.authedOnly
import com.wynnscribe.server.utils.userId
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.votes() {
    route("votes") {
        post {
            val user = call.authedOnly()
            val body = call.receive<Vote.Create>()
            val translationId = call.translationId()
            val has = transaction { Translations.has(translationId) }
            if(!has) { throw BadRequestException("Invalid translation id") }
            val inserted = transaction { Votes.createOrUpdate(body, user.userId(), translationId) }
            if(inserted == null) {
                call.respond(HttpStatusCode.BadRequest)
            } else {
                call.respond(HttpStatusCode.OK, inserted)
            }
        }
    }
}