package com.example.swcook.domain.entity

import java.util.*

data class RecipeEntity(
    val id: UUID? = null,
    val title: String,
    val description: String,
    val cookingTime: Int = 0,
    val datePublished: Date? = null,
    val steps: List<StepEntity>? = null,
    val ingredients: List<IngredientEntity>? = null
)

data class StepEntity(
    val id: UUID? = null,
    val position: Int = 0,
    val description: String,
    val enabled: Boolean = true,
    val prepTime: Int = 0
)

data class IngredientEntity(
    val id: UUID? = null,
    val name: String,
    val description: String? = null,
    val type: String? = null
)
