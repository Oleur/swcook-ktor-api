package com.example.swcook

import com.example.swcook.controller.ingredients
import com.example.swcook.controller.recipes
import com.example.swcook.core.di.appModules
import com.example.swcook.core.ktor.Environment.isDev
import com.example.swcook.core.ktor.converters
import com.example.swcook.core.ktor.exceptions
import com.example.swcook.core.moshi.adapters
import com.example.swcook.core.moshi.moshi
import com.example.swcook.data.AppDatabase
import com.zaxxer.hikari.HikariDataSource
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.dataconversion.DataConversion
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.resources.Resources
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(DataConversion) {
        converters()
    }

    install(Resources)

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
        if (application.isDev) {
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
