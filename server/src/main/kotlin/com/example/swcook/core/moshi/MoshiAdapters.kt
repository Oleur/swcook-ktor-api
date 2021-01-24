package com.example.swcook.core.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import java.util.*

fun Moshi.Builder.adapters() {
    add(UUIDAdapter)
    add(Date::class.java, Rfc3339DateJsonAdapter())
}

object UUIDAdapter {
    @FromJson
    fun fromJson(value: String): UUID = UUID.fromString(value)

    @ToJson
    fun toJson(value: UUID): String = value.toString()
}
