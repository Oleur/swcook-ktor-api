package com.example.swcook.controller

import com.example.swcook.Routes
import com.example.swcook.core.pagination.Paging.getPageRequest
import com.example.swcook.domain.entity.RecipeEntity
import com.example.swcook.domain.service.IngredientService
import com.example.swcook.domain.service.RecipeService
import com.example.swcook.domain.service.StepService
import com.example.swcook.front.mapper.toEntity
import com.example.swcook.front.models.AddIngredientToRecipeRequest
import com.example.swcook.front.models.GetRecipesResponse
import com.example.swcook.front.models.PatchRecipeRequestParameter
import com.example.swcook.front.models.PostRecipeRequest
import com.example.swcook.front.models.PostRecipeResponse
import com.example.swcook.front.models.PostStepRequest
import com.example.swcook.front.models.PostStepResponse
import com.example.swcook.front.renderer.renderer
import com.example.swcook.front.validation.validate
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.locations.KtorExperimentalLocationsAPI
import io.ktor.server.locations.delete
import io.ktor.server.locations.get
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.ktor.ext.inject
import io.ktor.server.locations.patch as locationsPatch
import io.ktor.server.locations.post as locationsPost

@KtorExperimentalLocationsAPI
fun Route.recipes() {

    val recipeService: RecipeService by inject()
    val ingredientService: IngredientService by inject()
    val stepService: StepService by inject()

    get<Routes.Recipes> {
        val page = call.parameters.getPageRequest()

        val pagedData = recipeService.getAll(page)

        val recipes = pagedData.items.map(RecipeEntity::renderer)
        val response = GetRecipesResponse(
            recipes = recipes,
            previousPage = pagedData.previous,
            nextPage = pagedData.next,
            total = pagedData.total
        )
        call.respond(HttpStatusCode.OK, response)
    }

    locationsPost<Routes.Recipes> {
        val request = withContext(Dispatchers.IO) {
            call.receive<PostRecipeRequest>()
        }
        request.validate()
        val created = recipeService.add(request.recipe.toEntity())
        if (created != null) {
            val response = PostRecipeResponse(recipe = created.renderer())
            call.respond(HttpStatusCode.Created, response)
        } else {
            call.respond(HttpStatusCode.Conflict)
        }
    }

    get<Routes.Recipes.ByUid> { route ->
        val recipe = recipeService.get(route.uid)
        if (recipe != null) {
            call.respond(HttpStatusCode.OK, recipe.renderer())
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }

    locationsPatch<Routes.Recipes.ByUid> { request ->
        val payload = withContext(Dispatchers.IO) {
            call.receive<PatchRecipeRequestParameter>()
        }
        payload.validate()
        val updated = recipeService.updateTitle(request.uid, payload.title)
        if (updated) {
            call.respond(HttpStatusCode.NoContent)
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }

    delete<Routes.Recipes.ByUid> { request ->
        val deleted = recipeService.delete(request.uid)
        if (deleted) {
            call.respond(HttpStatusCode.NoContent)
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    }

    // Add Ingredients to recipe
    locationsPost<Routes.Recipes.ByUid.Ingredients> { route ->
        val request = withContext(Dispatchers.IO) {
            call.receive<AddIngredientToRecipeRequest>()
        }
        request.validate()

        val added = ingredientService.addIngredientsToRecipe(route.app.uid, request.ingredients.filterNotNull())
        if (added) {
            call.respond(HttpStatusCode.Accepted)
        } else {
            call.respond(HttpStatusCode.Conflict)
        }
    }


    locationsPost<Routes.Recipes.ByUid.Steps> {

    }

    // Add Ingredients to recipe
    locationsPost<Routes.Recipes.ByUid.Steps> { route ->
        val request = withContext(Dispatchers.IO) {
            call.receive<PostStepRequest>()
        }
        request.validate()

        val created = stepService.createStep(route.app.uid, request.toEntity())
        if (created != null) {
            val response = PostStepResponse(step = created.renderer())
            call.respond(HttpStatusCode.Created, response)
        } else {
            call.respond(HttpStatusCode.Conflict)
        }
    }
}
