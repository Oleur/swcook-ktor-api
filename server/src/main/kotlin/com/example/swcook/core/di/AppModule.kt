package com.example.swcook.core.di

import com.example.swcook.data.AppDatabase
import com.example.swcook.data.repository.IngredientRepository
import com.example.swcook.data.repository.RecipeIngredientRepository
import com.example.swcook.data.repository.RecipeRepository
import com.example.swcook.data.repository.StepRepository
import com.example.swcook.domain.service.IngredientService
import com.example.swcook.domain.service.RecipeService
import com.example.swcook.domain.service.StepService
import com.typesafe.config.ConfigFactory
import io.ktor.config.*
import io.ktor.util.*
import org.koin.dsl.module

@KtorExperimentalAPI
val dataModule = module {
    single { AppDatabase(get()) }

    single { RecipeRepository() }
    single { IngredientRepository() }
    single { StepRepository() }
    single { RecipeIngredientRepository() }
}

@KtorExperimentalAPI
val domainModule = module {
    single { RecipeService(get()) }
    single { IngredientService(get(), get()) }
    single { StepService(get()) }
}

@KtorExperimentalAPI
val coreModule = module {
    single { provideKtorConfig() }
}

@KtorExperimentalAPI
private fun provideKtorConfig(): ApplicationConfig {
    return HoconApplicationConfig(ConfigFactory.load())
}

@KtorExperimentalAPI
val appModules = listOf(dataModule, domainModule, coreModule)
