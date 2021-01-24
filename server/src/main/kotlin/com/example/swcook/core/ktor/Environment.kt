package com.example.swcook.core.ktor

import io.ktor.application.*
import io.ktor.util.*

@KtorExperimentalAPI
object Environment {
    val Application.envKind get() = environment.config.property("ktor.environment").getString()
    val Application.isDev get() = envKind == "dev"
    val Application.isProd get() = envKind != "dev"
}

