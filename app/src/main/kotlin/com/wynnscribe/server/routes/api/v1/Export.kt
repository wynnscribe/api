package com.wynnscribe.server.routes.api.v1

import com.wynnscribe.models.Export
import com.wynnscribe.models.Project
import com.wynnscribe.server.databases.Projects
import com.wynnscribe.server.utils.adminOnly
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

fun Route.export() {
    route("export") {
        post {
            val adminOnly = call.adminOnly()
            val body = call.receive<Export.Create>()
            when(body.format) {
                "json" -> {
                    val exports = File("./exports")
                    if(!exports.exists()) { exports.mkdirs() }
                    transaction {
                        Projects.allJson().forEach { (languageId, json) ->
                            val encoded = Json.encodeToString(json)
                            exports.resolve("${languageId.value}.json").writeText(encoded)
                        }
                    }
                    call.respond(HttpStatusCode.Created)
                }
                else -> {
                    throw BadRequestException("this format type is not found.")
                }
            }
        }
    }
}