package com.wynnscribe.server.routes.api.v1

import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Projects
import com.wynnscribe.server.utils.adminOnly
import com.wynnscribe.server.utils.authedOnly
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

fun RoutingCall.projectId(): Project.ProjectId = Project.ProjectId(this.pathParameters["projectId"]?.toLongOrNull()?:throw BadRequestException("Invalid project id."))

fun Route.projects() {
    route("projects") {
        post {
            call.adminOnly()
            val body = call.receive<Project.Create>()
            val created = transaction { Projects.create(body) }
            if(created == null) {
                call.respond(HttpStatusCode.BadRequest)
            } else {
                call.respond(HttpStatusCode.Created, created)
            }
        }

        get {
            call.authedOnly()
            val returned = transaction { Projects.all() }
            call.respond(returned)
        }

        route("{projectId}") {

            originals()

            patch {
                call.adminOnly()
                val projectId = call.projectId()
                val body = call.receive<Project.Update>()
                val updated = transaction { Projects.update(projectId, body) }
                if(updated == null) {
                    call.respond(HttpStatusCode.BadRequest)
                } else {
                    call.respond(updated)
                }
            }

            get {
                call.authedOnly()
                val projectId = call.projectId()
                val returned = transaction { Projects.get(projectId) }
                if(returned == null) {
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    call.respond(returned)
                }
            }

            delete {
                call.adminOnly()
                val projectId = call.projectId()
                val has = transaction { Projects.has(projectId) }
                if(has) {
                    transaction { Projects.delete(projectId) }
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}