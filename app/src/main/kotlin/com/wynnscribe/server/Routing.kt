package com.wynnscribe.server

import com.wynnscribe.server.exceptions.UnauthorizedException
import com.wynnscribe.server.routes.api.v1.apiV1
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when(cause) {
                is UnauthorizedException -> call.respond(HttpStatusCode.Unauthorized)
                else -> {
                    cause.printStackTrace()
                    call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
                }
            }
        }
    }
    install(AutoHeadResponse)
    routing {
        apiV1()
    }
}
