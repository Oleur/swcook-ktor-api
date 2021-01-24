package com.example.swcook.core.pagination

import com.example.swcook.core.pagination.Paging.getPageRequest
import io.ktor.http.*
import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object PagingTest : Spek({

    Feature("Paging") {

        Scenario("Should get first page and default count when nor page or count parameters are set") {

            val params = mockk<Parameters>()

            Given("") {
                every { params["page"] } returns null
                every { params["count"] } returns null
            }

            lateinit var page: Page

            When("") {
                page = params.getPageRequest()
            }

            Then("") {
                page.page `should be equal to` Paging.FIRST_PAGE
                page.count `should be equal to` Paging.DEFAULT_PAGE_SIZE
            }
        }

        Scenario("Should get second page and given count from request parameters") {

            val params = mockk<Parameters>()

            Given("") {
                every { params["page"] } returns "2"
                every { params["count"] } returns "10"
            }

            lateinit var page: Page

            When("") {
                page = params.getPageRequest()
            }

            Then("") {
                page.page `should be equal to` 2
                page.count `should be equal to` 10
            }
        }
    }

    Feature("Page") {

        Scenario("Should page offset be 0 when first page is requested") {

            val index = 1
            val count = 20

            lateinit var page: Page

            When("") {
                page = Page(index, count)
            }

            Then("") {
                page.offset `should be equal to` 0
            }
        }

        Scenario("Should page offset be 20 when second page with a count of 20 is requested") {

            val index = 2
            val count = 20

            lateinit var page: Page

            When("") {
                page = Page(index, count)
            }

            Then("") {
                page.offset `should be equal to` 20
            }
        }
    }

    Feature("PagedData") {

        Scenario("Should have previous and next page when requesting the second paged data") {

            val items = listOf<Any>(1, 2, 3)
            val total = 10L

            lateinit var data: PagedData<Any>

            When("") {
                val page = Page(2, 3)
                data = page.data(items, total)
            }

            Then("") {
                data.previous `should be equal to` 1
                data.next `should be equal to` 3
            }
        }
    }
})
