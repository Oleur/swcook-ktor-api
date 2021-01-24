package com.example.swcook.front.renderer

import com.example.swcook.domain.entity.IngredientEntity
import com.example.swcook.domain.entity.RecipeEntity
import com.example.swcook.domain.entity.StepEntity
import com.example.swcook.front.models.Ingredient
import com.example.swcook.front.models.Recipe
import com.example.swcook.front.models.Step

fun RecipeEntity.renderer() = Recipe(
        id = this.id!!,
        title = title,
        description = description,
        cookingTime = cookingTime,
        datePublished = datePublished,
        steps = steps?.map(StepEntity::renderer),
        ingredients = ingredients?.map(IngredientEntity::renderer)
)

fun StepEntity.renderer() = Step(
        id = id!!,
        position = position,
        description = description,
        prepTime = prepTime
)

fun IngredientEntity.renderer() = Ingredient(
        id = id!!,
        name = name,
        description = description,
        type = type
)
