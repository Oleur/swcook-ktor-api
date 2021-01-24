package com.example.swcook.core.exposed.postgres

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Function
import org.jetbrains.exposed.sql.QueryBuilder
import org.jetbrains.exposed.sql.append

fun <T> Column<T>.distinctOn(): Function<T> = DistinctOn(this)

class DistinctOn<T>(private val column: Column<T>) : Function<T>(column.columnType) {
    override fun toQueryBuilder(queryBuilder: QueryBuilder) = queryBuilder {
        append("DISTINCT ON (", column, ") ", column)
    }
}
