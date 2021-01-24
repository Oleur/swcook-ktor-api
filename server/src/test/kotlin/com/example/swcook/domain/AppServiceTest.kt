package com.example.swcook.domain


import com.example.swcook.core.pagination.Page
import com.example.swcook.data.repository.RecipeRepository
import com.example.swcook.domain.entity.RecipeEntity
import com.example.swcook.domain.service.RecipeService
import com.nhaarman.mockitokotlin2.description
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should not be`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import java.util.*

object AppServiceTest : Spek({

    Feature("AppService Test") {

        val repository by memoized { mockk<RecipeRepository>(relaxed = true) }
        val service by memoized { RecipeService(repository) }

        Scenario("Should get first page") {

            lateinit var page: Page

            Given("") {
                page = Page(page = 1)
            }

            When("") {
                runBlocking {
                    service.getAll(page)
                }
            }

            Then("") {
                coVerify { repository.getAllPaginated(page) }
                confirmVerified(repository)
            }
        }

        Scenario("Should add a new application to the database") {
            // TODO
        }

        Scenario("Should return null when application already exists") {
            // TODO
        }

        Scenario("Should update the name of an existing application") {

            lateinit var appId: UUID
            lateinit var name: String

            Given("") {
                appId = UUID.randomUUID()
                name = "plop"
            }

            When("") {
                runBlocking {
                    service.updateTitle(appId, name)
                }
            }

            Then("") {
                coVerify { repository.updateTitle(appId, name) }
                confirmVerified(repository)
            }
        }

        Scenario("Should delete existing application") {

            lateinit var appId: UUID

            Given("") {
                appId = UUID.randomUUID()
            }

            When("") {
                runBlocking {
                    service.delete(appId)
                }
            }

            Then("") {
                coVerify { repository.delete(appId) }
                confirmVerified(repository)
            }
        }
    }
})
