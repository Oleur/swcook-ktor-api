package com.example.swcook.data.repository

import com.example.swcook.core.pagination.Page
import com.example.swcook.core.pagination.PagedData
import com.example.swcook.core.pagination.data
import com.example.swcook.data.mapper.toEntity
import com.example.swcook.data.model.Ingredient
import com.example.swcook.data.model.Recipe
import com.example.swcook.data.model.RecipeTable
import com.example.swcook.data.model.Step
import com.example.swcook.domain.entity.RecipeEntity
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class RecipeRepository {

    // CREATE
    suspend fun insert(recipe: RecipeEntity): RecipeEntity = newSuspendedTransaction {
        // ⚠️ we don't check if current recipe already exists
        Recipe.new {
            title = recipe.title
            description = recipe.description
            cookingTime = recipe.cookingTime
        }.toEntity()
    }

    // READ
    suspend fun get(id: UUID): RecipeEntity? = newSuspendedTransaction {
        val recipe = Recipe.findById(id)
        recipe?.toEntity()?.copy(
            steps = recipe.steps.filter { it.enabled }.map(Step::toEntity),
            ingredients = recipe.ingredients.map(Ingredient::toEntity)
        )
    }

    suspend fun getAllPaginated(page: Page): PagedData<RecipeEntity> = newSuspendedTransaction {
        val total = Recipe.all().count()
        val entities = Recipe.all()
            .orderBy(RecipeTable.createAt to SortOrder.DESC)
            .limit(page.count, page.offset)
            .map(Recipe::toEntity)
        page.data(entities, total)
    }

    // UPDATE
    suspend fun updateTitle(id: UUID, title: String): Boolean = newSuspendedTransaction {
        val recipe = Recipe.findById(id)
        recipe?.title = title
        recipe != null
    }

    // DELETE
    suspend fun delete(id: UUID): Boolean = newSuspendedTransaction {
        val recipe = Recipe.findById(id)
        recipe?.delete()
        recipe != null
    }
}
