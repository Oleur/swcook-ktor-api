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
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.config.HoconApplicationConfig
import org.koin.dsl.module

val dataModule = module {
    single { AppDatabase(get()) }

    single { RecipeRepository() }
    single { IngredientRepository() }
    single { StepRepository() }
    single { RecipeIngredientRepository() }
}

val domainModule = module {
    single { RecipeService(get()) }
    single { IngredientService(get(), get()) }
    single { StepService(get()) }
}

val coreModule = module {
    single { provideKtorConfig() }
}

private fun provideKtorConfig(): ApplicationConfig {
    return HoconApplicationConfig(ConfigFactory.load())
}

val appModules = listOf(dataModule, domainModule, coreModule)
