package com.emeka.configuration

import com.emeka.service.AccountingService
import com.emeka.service.FeeService
import com.tigerbeetle.Client
import com.tigerbeetle.UInt128
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {

    val tigerBeetleClusterId =
        environment.config.property("tigerbeetle.cluster-id").getString().toLong()
    val tigerBeetleReplicaAddressPort =
        environment.config.property("tigerbeetle.port").getString()

    install(Koin) {
        slf4jLogger()
        modules(serviceModule(tigerBeetleClusterId, tigerBeetleReplicaAddressPort))
    }
}

fun serviceModule(
    clusterId: Long,
    replicaAddress: String
) = module {
    single {
        Client(
            UInt128.asBytes(clusterId),
            arrayOf(replicaAddress)
        )
    }
    single { AccountingService(get()) }
    single { FeeService(get()) }
}
