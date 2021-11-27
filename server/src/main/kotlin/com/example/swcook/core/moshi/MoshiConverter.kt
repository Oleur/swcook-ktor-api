package com.example.swcook.core.moshi

import com.squareup.moshi.Moshi
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.util.pipeline.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*
import okio.buffer
import okio.source
import kotlin.reflect.jvm.jvmErasure

// Source: https://github.com/rharter/ktor-moshi - reimplemented because used kotlinx.io which has been removed in ktor since 1.3.0
class MoshiConverter(private val moshi: Moshi = Moshi.Builder().build()) : ContentConverter {
    override suspend fun convertForReceive(context: PipelineContext<ApplicationReceiveRequest, ApplicationCall>): Any? {
        val request = context.subject
        val channel = request.value as? ByteReadChannel ?: return null
        val source = channel.toInputStream().source().buffer()
        val type = request.typeInfo.jvmErasure
        return kotlin.runCatching {
            moshi.adapter(type.javaObjectType).fromJson(source)
        }.getOrNull()
    }

    override suspend fun convertForSend(
        context: PipelineContext<Any, ApplicationCall>,
        contentType: ContentType,
        value: Any
    ): Any? {
        return kotlin.runCatching {
            TextContent(
                moshi.adapter(value.javaClass).toJson(value),
                contentType.withCharset(context.call.suitableCharset())
            )
        }.getOrNull()
    }
}

fun ContentNegotiation.Configuration.moshi(moshi: Moshi = Moshi.Builder().build()) {
    val converter = MoshiConverter(moshi)
    register(ContentType.Application.Json, converter)
}

fun ContentNegotiation.Configuration.moshi(block: Moshi.Builder.() -> Unit) {
    val builder = Moshi.Builder()
    builder.apply(block)
    val converter = MoshiConverter(builder.build())
    register(ContentType.Application.Json, converter)
}
