package com.emeka

import com.emeka.configuration.configureFrameworks
import com.emeka.configuration.configureHTTP
import com.emeka.configuration.configureRouting
import com.emeka.configuration.configureSerialization
import com.emeka.configuration.configureStatusPages
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
