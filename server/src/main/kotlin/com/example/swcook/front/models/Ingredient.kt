/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: SWCook API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.example.swcook.front.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.UUID

/**
 * @property id
 * @property name
 * @property description
 * @property type
 * @property recipeId
 */
@JsonClass(generateAdapter = true)
data class Ingredient(
    @Json(name = "id") @field:Json(name = "id") var id: UUID,
    @Json(name = "name") @field:Json(name = "name") var name: String,
    @Json(name = "description") @field:Json(name = "description") var description: String? = null,
    @Json(name = "type") @field:Json(name = "type") var type: String? = null,
    @Json(name = "recipe_id") @field:Json(name = "recipe_id") var recipeId: UUID? = null
)
