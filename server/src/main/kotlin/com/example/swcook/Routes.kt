package com.example.swcook

import io.ktor.locations.*
import java.util.*

@KtorExperimentalLocationsAPI
object Routes {

    @Location("/recipes")
    class Recipes {

        @Location("/{uid}")
        data class ByUid(val recipes: Recipes, val uid: UUID) {

            @Location("/ingredients")
            data class Ingredients(val app: Recipes.ByUid) {

                @Location("/{ingredientUid}")
                data class ByUid(val ingredients: Ingredients, val ingredientUid: UUID)
            }

            @Location("/steps")
            data class Steps(val app: Recipes.ByUid) {

                @Location("/{stepUid}")
                data class ByUid(val ingredients: Steps, val stepUid: UUID)
            }
        }
    }

    @Location("/ingredients")
    class Ingredients {

        @Location("/{ingredientId}")
        data class ByUid (val parent: Ingredients, val ingredientId: UUID)
    }
}
