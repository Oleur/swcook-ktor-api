package com.example.swcook.core.pagination

import io.ktor.http.*

object Paging {

    const val DEFAULT_PAGE_SIZE = 25
    const val FIRST_PAGE = 1

    private const val PAGE_PARAM = "page"
    private const val COUNT_PARAM = "count"

    fun Parameters.getPageRequest(): Page {
        val page = getPage(this[PAGE_PARAM])
        val count = getCount(this[COUNT_PARAM])

        return Page(page, count)
    }

    private fun getPage(param: String?): Int {
        return param?.toIntOrNull()?.let { page ->
            if (page < FIRST_PAGE) FIRST_PAGE else page
        } ?: FIRST_PAGE
    }

    private fun getCount(param: String?): Int {
        return param?.toIntOrNull()?.let { size ->
            if (size <= 0) DEFAULT_PAGE_SIZE else size
        } ?: DEFAULT_PAGE_SIZE
    }
}

open class Page(val page: Int = Paging.FIRST_PAGE, val count: Int = Paging.DEFAULT_PAGE_SIZE) {

    val offset: Long
        get() = ((page - 1) * count).toLong()
}

class PagedData<T>(val items: List<T>, val total: Long, page: Int, count: Int) : Page(page, count) {

    val previous: Int? by lazy {
        val totalPage = getTotalPage(total.toInt(), count)
        val hasPreviousPage = page in (Paging.FIRST_PAGE + 1)..totalPage
        if (hasPreviousPage) page - 1 else null
    }

    val next: Int? by lazy {
        if (count == items.size) page + 1 else null
    }

    private fun getTotalPage(total: Int, count: Int): Int {
        val extra = if (total % count == 0) 0 else 1
        return (total / count) + extra
    }
}

fun <T> Page.data(items: List<T>, total: Long): PagedData<T> {
    return PagedData(items, total, page, count)
}
