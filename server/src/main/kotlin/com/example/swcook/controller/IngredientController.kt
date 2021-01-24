package com.example.swcook.controller

import com.example.swcook.Routes
import com.example.swcook.core.pagination.Paging.getPageRequest
import com.example.swcook.domain.entity.IngredientEntity
import com.example.swcook.domain.service.IngredientService
import com.example.swcook.front.mapper.toEntity
import com.example.swcook.front.models.GetIngredientsResponse
import com.example.swcook.front.models.PostIngredientRequest
import com.example.swcook.front.models.PostIngredientResponse
import com.example.swcook.front.renderer.renderer
import com.example.swcook.front.validation.validate
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.ktor.ext.inject

@KtorExperimentalLocationsAPI
fun Route.ingredients() {

    val service: IngredientService by inject()

    get<Routes.Ingredients> {
        val page = call.parameters.getPageRequest()

        val pagedData = service.getAll(page)

        val ingredients = pagedData.items.map(IngredientEntity::renderer)
        val response = GetIngredientsResponse(
            ingredients = ingredients,
            previousPage = pagedData.previous,
            nextPage = pagedData.next,
            total = pagedData.total
        )
        call.respond(HttpStatusCode.OK, response)
    }

    post<Routes.Ingredients> {
        val request = withContext(Dispatchers.IO) {
            call.receive<PostIngredientRequest>()
        }
        request.validate()
        val created = service.add(request.toEntity())
        if (created != null) {
            val response = PostIngredientResponse(ingredient = created.renderer())
            call.respond(HttpStatusCode.Created, response)
        } else {
            call.respond(HttpStatusCode.Conflict)
        }
    }
}
