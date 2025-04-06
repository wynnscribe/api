package com.wynnscribe.server.routes.api.v1

import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import java.io.File

fun Route.downloads() {

    val exportsDir = File("./exports").absoluteFile.normalize()

    route("downloads") {
        get("{language}.json") {
            val language = call.pathParameters["language"]?.lowercase()?:return@get
            if("." in language || "/" in language || "\\" in language) {
                throw BadRequestException("Invalid language")
            }
            val file = exportsDir.resolve("${language}.json").absoluteFile.normalize()
            if(!file.startsWith(exportsDir)) { throw BadRequestException("Invalid language") }
            println(file)
            if(file.exists()) {
                call.respond(file.readText())
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}