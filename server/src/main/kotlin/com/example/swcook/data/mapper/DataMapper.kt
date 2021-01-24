package com.example.swcook.data.mapper

import com.example.swcook.core.extension.toUTCDate
import com.example.swcook.data.model.Ingredient
import com.example.swcook.data.model.Recipe
import com.example.swcook.data.model.Step
import com.example.swcook.domain.entity.IngredientEntity
import com.example.swcook.domain.entity.RecipeEntity
import com.example.swcook.domain.entity.StepEntity

fun Recipe.toEntity() = RecipeEntity(
    id = uid,
    title = title,
    description = description,
    cookingTime = cookingTime,
    datePublished = createAt.toUTCDate()
)

fun Step.toEntity() = StepEntity(
    id = uid,
    position = position,
    description = description,
    enabled = enabled,
    prepTime = prepTime
)

fun Ingredient.toEntity() = IngredientEntity(
    id = uid,
    name = name,
    description = description,
    type = type
)