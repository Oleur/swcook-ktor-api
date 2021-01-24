package com.example.swcook.data.repository

import com.example.swcook.core.pagination.Page
import com.example.swcook.core.pagination.PagedData
import com.example.swcook.core.pagination.data
import com.example.swcook.data.mapper.toEntity
import com.example.swcook.data.model.Recipe
import com.example.swcook.data.model.Step
import com.example.swcook.data.model.StepTable
import com.example.swcook.domain.entity.StepEntity
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

class StepRepository {

    suspend fun getAllByRecipe(recipeId: UUID, page: Page): PagedData<StepEntity> = newSuspendedTransaction {
        val allSteps = Step.find { StepTable.recipe eq recipeId }
        val total = allSteps.count()

        val steps = allSteps
                .orderBy(StepTable.createAt to SortOrder.DESC)
                .limit(page.count, page.offset)
                .map(Step::toEntity)

        page.data(steps, total)
    }

    suspend fun insert(recipeId: UUID, entity: StepEntity): StepEntity? = newSuspendedTransaction {
        Recipe.findById(recipeId)?.let { rec ->
            Step.new {
                position = entity.position
                description = entity.description
                enabled = entity.enabled
                prepTime = entity.prepTime
                recipe = rec
            }.toEntity()
        }
    }

    suspend fun delete(id: UUID): Boolean = newSuspendedTransaction {
        val step = Step.findById(id)
        step?.delete()
        step != null
    }

    suspend fun getStep(stepId: UUID): StepEntity? = newSuspendedTransaction {
        Step.findById(stepId)?.toEntity()
    }
}
