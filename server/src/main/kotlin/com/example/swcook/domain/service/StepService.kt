package com.example.swcook.domain.service

import com.example.swcook.data.repository.StepRepository
import com.example.swcook.domain.entity.StepEntity
import java.util.*

class StepService(private val repository: StepRepository) {

    suspend fun createStep(recipeId: UUID, step: StepEntity): StepEntity? {
        return repository.insert(recipeId, step)
    }
}