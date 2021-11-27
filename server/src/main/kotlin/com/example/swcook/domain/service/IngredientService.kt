package com.example.swcook.domain.service

import com.example.swcook.core.pagination.Page
import com.example.swcook.data.repository.IngredientRepository
import com.example.swcook.data.repository.RecipeIngredientRepository
import com.example.swcook.domain.entity.IngredientEntity
import java.util.*

class IngredientService(
    private val ingredientRepository: IngredientRepository,
    private val recipeIngredientRepository: RecipeIngredientRepository
) {

    suspend fun getAll(page: Page) = ingredientRepository.getAllPaginated(page)

    suspend fun get(uid: UUID) = ingredientRepository.get(uid)

    suspend fun add(recipe: IngredientEntity): IngredientEntity {
        // TODO check to avoid adding recipes multiple times
        return ingredientRepository.insert(recipe)
    }

    suspend fun getRecipeIngredients(recipeId: UUID) = ingredientRepository.getRecipeIngredients(recipeId)

    suspend fun addIngredientsToRecipe(recipeId: UUID, ingredients: List<UUID>): Boolean {
        return recipeIngredientRepository.addIngredientsToRecipe(recipeId, ingredients)
    }

    suspend fun addIngredientToRecipe(recipeId: UUID, ingredient: UUID): Boolean {
        return recipeIngredientRepository.addIngredientToRecipe(recipeId, ingredient)
    }
}
