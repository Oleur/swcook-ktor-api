package com.example.swcook

import com.example.swcook.core.serialization.UUIDSerializer
import io.ktor.resources.Resource
import kotlinx.serialization.Serializable
import java.util.*

object Routes {

    @Serializable
    @Resource("/recipes")
    class Recipes {

        @Serializable
        @Resource("/{uid}")
        data class ByUid(
            val recipes: Recipes,
            @Serializable(with = UUIDSerializer::class) val uid: UUID
        ) {

            @Serializable
            @Resource("/ingredients")
            data class Ingredients(val app: Recipes.ByUid) {

                @Serializable
                @Resource("/{ingredientUid}")
                data class ByUid(
                    val ingredients: Ingredients,
                    @Serializable(with = UUIDSerializer::class) val ingredientUid: UUID
                )
            }

            @Serializable
            @Resource("/steps")
            data class Steps(val app: Recipes.ByUid) {

                @Serializable
                @Resource("/{stepUid}")
                data class ByUid(
                    val ingredients: Steps,
                    @Serializable(with = UUIDSerializer::class) val stepUid: UUID
                )
            }
        }
    }

    @Serializable
    @Resource("/ingredients")
    class Ingredients {

        @Serializable
        @Resource("/{ingredientId}")
        data class ByUid (
            val parent: Ingredients,
            @Serializable(with = UUIDSerializer::class) val ingredientId: UUID
        )
    }
}
