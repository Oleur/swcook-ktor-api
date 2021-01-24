package com.example.swcook.core.ktor

import io.ktor.http.*
import io.ktor.response.*

object CallResponse {

    fun ApplicationResponse.created(location: String) {
        header("Location", location)
        status(HttpStatusCode.Created)
    }
}
