package com.wynnscribe.server.routes.api.v1

import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.apiV1() {
    route("/api/v1") {
        authenticate("auth-bearer") {
            languages()
            projects()
            export()
        }
        publicLanguages()
        downloads()
    }
}