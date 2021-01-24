package com.example.swcook.data.repository

import com.example.swcook.core.pagination.Page
import com.example.swcook.core.pagination.PagedData
import com.example.swcook.core.pagination.data
import com.example.swcook.data.mapper.toEntity
import com.example.swcook.data.model.Ingredient
import com.example.swcook.data.model.IngredientTable
import com.example.swcook.data.model.Recipe
import com.example.swcook.data.model.RecipeTable
import com.example.swcook.domain.entity.IngredientEntity
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class IngredientRepository {

    suspend fun insert(entity: IngredientEntity): IngredientEntity = newSuspendedTransaction {
        createIngredient(entity)
    }

    suspend fun getAllPaginated(page: Page): PagedData<IngredientEntity> = newSuspendedTransaction {
        val total = Ingredient.all().count()
        val entities = Ingredient.all()
            .orderBy(IngredientTable.createAt to SortOrder.DESC)
            .limit(page.count, page.offset)
            .map(Ingredient::toEntity)
        page.data(entities, total)
    }

    suspend fun get(id: UUID): IngredientEntity? = newSuspendedTransaction {
        Ingredient.findById(id)?.toEntity()
    }

    suspend fun getRecipeIngredients(recipeId: UUID) = newSuspendedTransaction {
        Recipe.findById(recipeId)?.ingredients?.map(Ingredient::toEntity)
    }

    suspend fun insertOrUpdate(update: IngredientEntity): IngredientEntity = newSuspendedTransaction {
        val device = Ingredient.findById(update.id!!)
        if (device != null) {
            updateDevice(device, update)
        } else {
            createIngredient(update)
        }
    }

    private fun updateDevice(device: Ingredient, update: IngredientEntity): IngredientEntity {
        device.name = update.name
        device.description = update.description
        device.type = update.type
        return device.toEntity()
    }

    private fun createIngredient(entity: IngredientEntity): IngredientEntity {
        return Ingredient.new {
            name = entity.name
            description = entity.description
            type = entity.type
        }.toEntity()
    }
}
