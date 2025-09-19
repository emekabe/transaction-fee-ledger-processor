package com.emeka.routing

import com.emeka.dto.request.TransactionRequest
import com.emeka.service.FeeService
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureTransactionRouting(feeService: FeeService) {
    routing {
        post("/transaction/fee") {
            val request = call.receive<TransactionRequest>()
            call.respond(feeService.chargeFee(request))
        }
    }
}