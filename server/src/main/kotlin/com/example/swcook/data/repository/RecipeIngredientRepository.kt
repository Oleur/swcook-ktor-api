package com.example.swcook.data.repository

import com.example.swcook.data.model.Ingredient
import com.example.swcook.data.model.Recipe
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class RecipeIngredientRepository {

    suspend fun addIngredientsToRecipe(uid: UUID, ingredients: List<UUID>): Boolean = newSuspendedTransaction {
        Recipe.findById(uid)?.let { recipe ->
            ingredients.forEach { ingredientId ->
                Ingredient.findById(ingredientId)?.let { ingredient ->
                    val newIngredients = recipe.ingredients.toMutableList()
                    val exist = newIngredients.contains(ingredient)
                    if (!exist) {
                        newIngredients.add(ingredient)
                        recipe.ingredients = SizedCollection(newIngredients)
                    }
                } != null
            }
        } != null
    }

    suspend fun addIngredientToRecipe(uid: UUID, ingredientId: UUID): Boolean = newSuspendedTransaction {
        Recipe.findById(uid)?.let { recipe ->
            Ingredient.findById(ingredientId)?.let { ingredient ->
                val ingredients = recipe.ingredients.toMutableList()
                val exist = ingredients.contains(ingredient)
                if (!exist) {
                    ingredients.add(ingredient)
                    recipe.ingredients = SizedCollection(ingredients)
                }
            } != null
        } != null
    }
}
