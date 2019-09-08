package br.com.ktor.app

import br.com.ktor.app.model.Person
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.lang.System.getenv

fun Application.module() {
    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            registerModule(JavaTimeModule())
        }
    }
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/") {
            call.respondText("hello world 2")
        }
        post("/my-post") {
            call.respond("OK")
        }
        post("/persons") {
            val person = call.receive<Person>()
            call.respond(mapOf("person" to person))
        }
    }
}

fun main() {
    embeddedServer(factory = Netty,
            port = getenv("PORT")?.toInt() ?: 8080,
            watchPaths = listOf("ktor-app"),
            module = Application::module).start(true)
}
