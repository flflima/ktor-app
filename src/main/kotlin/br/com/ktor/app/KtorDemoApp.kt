package br.com.ktor.app

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.lang.System.getenv

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/") {
            call.respondText("hello world 2")
        }
    }
}

fun main() {
    embeddedServer(Netty, getenv("PORT")?.toInt() ?: 8080, watchPaths = listOf("ktor-app"), module = Application::module).start(true)
}