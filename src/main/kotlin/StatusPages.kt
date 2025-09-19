package com.emeka

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            if(cause is IllegalArgumentException) {
                call.respondText(text = "400: $cause" , status = HttpStatusCode.BadRequest)
            } else {
                call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
            }
        }
        status(HttpStatusCode.NotFound) { call, status ->
            call.respondText(text = "404: Page Not Found", status = status)
        }
        statusFile(HttpStatusCode.Unauthorized, HttpStatusCode.PaymentRequired, filePattern = "error#.html")
    }
}
