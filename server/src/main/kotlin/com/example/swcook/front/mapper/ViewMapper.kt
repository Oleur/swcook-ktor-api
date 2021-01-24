package com.example.swcook.front.mapper

import com.example.swcook.domain.entity.IngredientEntity
import com.example.swcook.domain.entity.RecipeEntity
import com.example.swcook.domain.entity.StepEntity
import com.example.swcook.front.models.PostIngredientRequest
import com.example.swcook.front.models.PostRecipeRequestRecipe
import com.example.swcook.front.models.PostStepRequest
import com.example.swcook.front.models.Step

fun PostRecipeRequestRecipe.toEntity() = RecipeEntity(
    title = title,
    description = description,
    cookingTime = cookingTime
)

fun PostIngredientRequest.toEntity() = IngredientEntity(
    name = name,
    description = description,
    type = type
)

fun PostStepRequest.toEntity() = StepEntity(
    position = position,
    description = description,
    prepTime = prepTime
)
