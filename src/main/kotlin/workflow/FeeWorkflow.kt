package com.emeka.workflow

import com.emeka.workflow.dto.request.TransactionRequest
import com.emeka.workflow.dto.response.TransactionResponse
import dev.restate.sdk.annotation.Handler
import dev.restate.sdk.annotation.Service
import dev.restate.sdk.http.vertx.RestateHttpServer
import dev.restate.sdk.kotlin.*
import dev.restate.sdk.kotlin.endpoint.endpoint
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Service
class FeeWorkflow() {

    private val httpClient = HttpClient.newBuilder().build()
    private val json = Json { ignoreUnknownKeys = true }

    @Handler
    suspend fun chargeFee(ctx: Context, request: TransactionRequest): TransactionResponse {

        return ctx.runBlock("CallKtorFeeService") {

            println("Running CallKtorFeeService")
            val requestBody = json.encodeToString(request)

            val httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8082/transaction/fee"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build()


            val httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())

            println("Received fee response")

            json.decodeFromString<TransactionResponse>(httpResponse.body())
        }
    }

}

fun main() {
    println("Starting FeeWorkflow Restate Server...")
    val startTime = System.currentTimeMillis()

    RestateHttpServer.listen(endpoint {
        bind(FeeWorkflow())
    }, 8081)

    val endTime = System.currentTimeMillis()
    println("Started FeeWorkflow Restate Server in ${(endTime - startTime) / 1000} seconds")
}