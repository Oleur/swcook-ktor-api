package com.example.swcook.core.ktor

import com.squareup.moshi.JsonDataException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.log
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import org.valiktor.ConstraintViolationException

fun StatusPagesConfig.exceptions() {

    exception<ConstraintViolationException> { call, exception ->
        // Basic ConstraintViolationException handler
        val violations =
            exception.constraintViolations.map { violation -> "${violation.property}:${violation.constraint.name}" }
        call.respondText(status = HttpStatusCode.UnprocessableEntity) { violations.toString() }
    }

    exception<JsonDataException> { call, exception ->
        // Basic moshi JsonDataException handler
        call.respondText(status = HttpStatusCode.BadRequest) { exception.message.toString() }
    }

    exception<Throwable> { call, exception ->
        call.application.log.error("Unhandled exception", exception)
        call.respond(HttpStatusCode.InternalServerError)
    }
}
