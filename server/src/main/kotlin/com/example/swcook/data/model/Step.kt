package com.example.swcook.data.model

import com.example.swcook.core.exposed.ExtendedUUIDEntity
import com.example.swcook.core.exposed.ExtendedUUIDEntityClass
import com.example.swcook.core.exposed.ExtendedUUIDTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import java.util.*

object StepTable : ExtendedUUIDTable(name = "step") {
    val position: Column<Int> = integer(name = "position")
    val description: Column<String> = text(name = "description")
    val enabled: Column<Boolean> = bool("enabled").default(true)
    val prepTime: Column<Int> = integer(name = "prep_time").default(0)

    val recipe = reference("recipe", RecipeTable)
}

class Step(id: EntityID<UUID>) : ExtendedUUIDEntity(id, StepTable) {
    companion object : ExtendedUUIDEntityClass<Step>(StepTable)

    var position by StepTable.position
    var description by StepTable.description
    var enabled by StepTable.enabled
    var prepTime by StepTable.prepTime

    var recipe by Recipe referencedOn StepTable.recipe
}
