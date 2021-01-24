package com.example.swcook.core.ktor

import io.ktor.features.*
import io.ktor.util.*
import java.util.*

fun DataConversion.Configuration.converters() {

    convert<UUID> {
        decode { values, _ ->
            values.singleOrNull()?.let { value -> UUID.fromString(value) }
        }

        encode { value ->
            when (value) {
                null -> emptyList()
                is UUID -> listOf(value.toString())
                else -> throw DataConversionException("Cannot convert $value as UUID")
            }
        }
    }
}
