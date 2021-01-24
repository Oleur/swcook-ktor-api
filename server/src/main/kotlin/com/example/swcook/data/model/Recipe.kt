package com.example.swcook.data.model

import com.example.swcook.core.exposed.ExtendedUUIDEntity
import com.example.swcook.core.exposed.ExtendedUUIDEntityClass
import com.example.swcook.core.exposed.ExtendedUUIDTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import java.util.*

object RecipeTable : ExtendedUUIDTable(name = "recipe") {
    val title: Column<String> = text(name = "title")
    val description: Column<String> = text(name = "description")
    val cookingTime: Column<Int> = integer(name = "cooking_time").default(0)
}

class Recipe(id: EntityID<UUID>) : ExtendedUUIDEntity(id, RecipeTable) {
    companion object : ExtendedUUIDEntityClass<Recipe>(RecipeTable)

    var title by RecipeTable.title
    var description by RecipeTable.description
    var cookingTime by RecipeTable.cookingTime

    val steps by Step referrersOn StepTable.recipe
    var ingredients by Ingredient via RecipeIngredientTable
}
