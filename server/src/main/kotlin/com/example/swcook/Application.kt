package com.example.swcook

import com.example.swcook.controller.ingredients
import com.example.swcook.controller.recipes
import com.example.swcook.core.di.appModules
import com.example.swcook.core.ktor.Environment.isDev
import com.example.swcook.core.ktor.exceptions
import com.example.swcook.core.moshi.adapters
import com.example.swcook.core.ktor.converters
import com.example.swcook.core.moshi.moshi
import com.example.swcook.data.AppDatabase
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.koin.logger.SLF4JLogger

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalLocationsAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(DataConversion) {
        converters()
    }

    install(Locations)

    install(Koin) {
        SLF4JLogger()
        modules(appModules)
    }

    install(ContentNegotiation) {
        moshi {
            adapters()
        }
    }

    install(StatusPages) {
        exceptions()
    }

    val database: AppDatabase by inject()
    database.init()

    routing {
        if (isDev) {
            get("/") {
                call.respondText("Server is running 🏃‍️")
            }
        }

        get("/health_check") {
            val isAlive = (database.dataSource as HikariDataSource).isRunning
            call.respond(HttpStatusCode.OK, "database: $isAlive")
        }

        recipes()
        ingredients()
    }
}
