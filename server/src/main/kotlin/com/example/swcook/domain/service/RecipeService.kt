package com.example.swcook.domain.service

import com.example.swcook.core.pagination.Page
import com.example.swcook.data.repository.RecipeRepository
import com.example.swcook.domain.entity.RecipeEntity
import java.util.*

class RecipeService(private val recipeRepository: RecipeRepository) {

    suspend fun getAll(page: Page) = recipeRepository.getAllPaginated(page)

    suspend fun get(uid: UUID) = recipeRepository.get(uid)

    suspend fun add(recipe: RecipeEntity): RecipeEntity {
        // TODO check to avoid adding recipes multiple times
        return recipeRepository.insert(recipe)
    }

    suspend fun updateTitle(uid: UUID, title: String) = recipeRepository.updateTitle(uid, title)

    suspend fun delete(uid: UUID) = recipeRepository.delete(uid)

}
