package com.example.swcook.data.model

import com.example.swcook.core.exposed.ExtendedUUIDEntity
import com.example.swcook.core.exposed.ExtendedUUIDEntityClass
import com.example.swcook.core.exposed.ExtendedUUIDTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import java.util.*

object IngredientTable : ExtendedUUIDTable(name = "ingredient") {
    val name: Column<String> = text(name = "title")
    val description: Column<String?> = text(name = "description").nullable()
    val type: Column<String?> = text(name = "type").nullable()
}

class Ingredient(id: EntityID<UUID>) : ExtendedUUIDEntity(id, IngredientTable) {
    companion object : ExtendedUUIDEntityClass<Ingredient>(IngredientTable)

    var name by IngredientTable.name
    var description by IngredientTable.description
    var type by IngredientTable.type

    var recipes by Recipe via RecipeIngredientTable
}
