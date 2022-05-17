package com.example.swcook.core.moshi

import com.squareup.moshi.Moshi
import io.ktor.http.ContentType
import io.ktor.http.content.OutgoingContent
import io.ktor.http.content.TextContent
import io.ktor.http.withCharset
import io.ktor.serialization.ContentConverter
import io.ktor.server.plugins.contentnegotiation.ContentNegotiationConfig
import io.ktor.util.reflect.TypeInfo
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.charsets.Charset
import io.ktor.utils.io.jvm.javaio.toInputStream
import okio.buffer
import okio.source

// Source: https://github.com/rharter/ktor-moshi - reimplemented because used kotlinx.io which has been removed in ktor since 1.3.0
class MoshiConverter(private val moshi: Moshi = Moshi.Builder().build()) : ContentConverter {

    override suspend fun deserialize(
        charset: Charset,
        typeInfo: TypeInfo,
        content: ByteReadChannel
    ): Any? {
        val source = content.toInputStream().source().buffer()
        return moshi.adapter(typeInfo.type.javaObjectType).fromJson(source)
    }

    override suspend fun serialize(
        contentType: ContentType,
        charset: Charset,
        typeInfo: TypeInfo,
        value: Any
    ): OutgoingContent {
        return TextContent(
            moshi.adapter(value.javaClass).toJson(value),
            contentType.withCharset(charset)
        )
    }
}

fun ContentNegotiationConfig.moshi(moshi: Moshi = Moshi.Builder().build()) {
    val converter = MoshiConverter(moshi)
    register(ContentType.Application.Json, converter)
}

fun ContentNegotiationConfig.moshi(block: Moshi.Builder.() -> Unit) {
    val builder = Moshi.Builder()
    builder.apply(block)
    val converter = MoshiConverter(builder.build())
    register(ContentType.Application.Json, converter)
}
