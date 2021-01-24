package com.example.swcook.core.extension

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

fun LocalDateTime.toUTCDate(): Date = Date.from(toInstant(ZoneOffset.UTC))
