package com.example.swcook.data.model

import org.jetbrains.exposed.sql.Table

object RecipeIngredientTable : Table() {
    val recipe = reference(RecipeTable.tableName, RecipeTable)
    val ingredient = reference(IngredientTable.tableName, IngredientTable)

    override val primaryKey = PrimaryKey(recipe, ingredient)
}
