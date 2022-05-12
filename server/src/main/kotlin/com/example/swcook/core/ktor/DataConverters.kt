package com.example.swcook.core.ktor

import io.ktor.util.converters.DataConversion
import io.ktor.util.converters.DataConversionException
import java.util.*

fun DataConversion.Configuration.converters() {

    convert<UUID> {
        decode { values ->
            UUID.fromString(values.first())
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
