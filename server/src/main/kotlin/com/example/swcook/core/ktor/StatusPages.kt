package com.example.swcook.core.ktor

import com.squareup.moshi.JsonDataException
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import org.valiktor.ConstraintViolationException

fun StatusPages.Configuration.exceptions() {

    exception<ConstraintViolationException> { exception ->
        // Basic ConstraintViolationException handler
        val violations = exception.constraintViolations.map { violation -> "${violation.property}:${violation.constraint.name}" }
        call.respondText(status = HttpStatusCode.UnprocessableEntity) { violations.toString() }
    }

    exception<JsonDataException> { exception ->
        // Basic moshi JsonDataException handler
        call.respondText(status = HttpStatusCode.BadRequest) { exception.message.toString() }
    }

    exception<Throwable> { exception ->
        application.log.error("Unhandled exception", exception)
        call.respond(HttpStatusCode.InternalServerError)
    }
}
