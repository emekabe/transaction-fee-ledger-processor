val koin_version: String by project
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val tigerbeetle_version: String by project
val restate_version: String by project
val kx_serialization_version: String by project
val mockk_version: String by project

plugins {
    kotlin("jvm") version "2.2.20"
    id("io.ktor.plugin") version "3.3.0"
    id("com.google.devtools.ksp") version "2.2.10-2.0.2"
    kotlin("plugin.serialization") version "2.2.20"
}

group = "com.emeka"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

dependencies {
    implementation("io.ktor:ktor-server-cors")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-jackson")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("io.ktor:ktor-server-status-pages:${ktor_version}")

    implementation("com.tigerbeetle:tigerbeetle-java:$tigerbeetle_version")

    ksp("dev.restate:sdk-api-kotlin-gen:$restate_version")

    implementation("dev.restate:sdk-kotlin-http:$restate_version")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kx_serialization_version")
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.124.Final:osx-aarch_64")

    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("io.mockk:mockk:$mockk_version")
}


kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}
