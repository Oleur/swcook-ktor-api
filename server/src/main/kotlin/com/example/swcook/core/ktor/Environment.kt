package com.example.swcook.core.ktor

import io.ktor.server.application.Application

object Environment {
    val Application.envKind get() = environment.config.property("ktor.environment").getString()
    val Application.isDev get() = envKind == "dev"
    val Application.isProd get() = envKind != "dev"
}

