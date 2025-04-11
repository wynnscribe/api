package com.wynnscribe.server.routes.api.v1

import com.wynnscribe.models.Language
import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Originals
import com.wynnscribe.server.databases.Translations
import com.wynnscribe.server.databases.Votes
import com.wynnscribe.server.utils.adminOnly
import com.wynnscribe.server.utils.authedOnly
import com.wynnscribe.server.utils.isNeedReview
import com.wynnscribe.server.utils.userId
import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction

fun RoutingCall.originalId(): Project.Original.OriginalId = Project.Original.OriginalId(this.pathParameters["originalId"]?.toLongOrNull()?:throw BadRequestException("Invalid original id."))

fun RoutingCall.languageId(): Language.LanguageId = Language.LanguageId(this.queryParameters["lang"]?:throw BadRequestException("Invalid language id."))

fun Route.originals() {
    route("originals") {
        route("details") {
            get {
                call.authedOnly()
                val projectId = call.projectId()
                val limit = call.queryParameters["limit"]?.toIntOrNull()?:30
                if(limit !in 1..30) { throw BadRequestException("Invalid limit range. require in 1..30") }
                val offset = call.queryParameters["offset"]?.toLongOrNull()?:0
                val languageId = call.languageId()
                val originals = transaction { Originals.list(projectId, limit, offset) }
                val translations = transaction { Translations.list(originals.map(Project.Original::id), languageId) }
                val votes = transaction { Votes.list(translations.map(Project.Original.Translation::id)) }
                call.respond<List<Project.Original.Details>>(originals.map { Project.Original.Details.of(it, translations, votes, originals) })
            }
        }

        get {
            call.authedOnly()
            val projectId = call.projectId()
            val limit = call.queryParameters["limit"]?.toIntOrNull()?:30
            if(limit !in 1..30) { throw BadRequestException("Invalid limit range. require in 1..30") }
            val offset = call.queryParameters["offset"]?.toLongOrNull()?:0
            val returned = transaction { Originals.list(projectId, limit, offset) }
            call.respond(returned)
        }

        post {
            call.adminOnly()
            val projectId = call.projectId()
            val body = call.receive<Project.Original.Create>()
            val created = transaction { Originals.create(body, projectId = projectId) }
            if(created == null) {
                call.respond(HttpStatusCode.BadRequest)
            } else {
                call.respond(HttpStatusCode.Created, created)
            }
        }

        post("translations") {
            val user = call.authedOnly()
            val languageId = call.languageId()
            val body = call.receive<List<Project.Original.Translation.CreateMany>>()
            body.forEach { translation ->
                if(isNeedReview(translation.text.value)) {
                    translation.status = Project.Original.Translation.Status.NeedsReview
                }
            }
            transaction { Translations.batchCreateOrUpdate(body, userId = user.userId(), languageId = languageId) }
            call.respond(HttpStatusCode.Created)
        }

        route("{originalId}") {

            translations()

            patch {
                call.adminOnly()
                val originalId = call.originalId()
                val body = call.receive<Project.Original.Update>()
                val updated = transaction { Originals.update(originalId, body) }
                if(updated == null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    call.respond(updated)
                }
            }

            delete {
                call.adminOnly()
                val originalId = call.originalId()
                val has = transaction { Originals.has(originalId) }
                if(has) {
                    transaction { Originals.delete(originalId) }
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}