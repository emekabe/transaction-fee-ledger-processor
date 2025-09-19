package com.emeka

import com.emeka.routing.configureTransactionRouting
import io.ktor.server.application.*
import org.koin.ktor.ext.get

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureHTTP()
    configureSerialization()
    configureFrameworks()
    configureRouting()
    configureStatusPages()
    configureTransactionRouting(get())
}
