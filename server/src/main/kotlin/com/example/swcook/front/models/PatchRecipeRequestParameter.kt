/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: SWCook API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.example.swcook.front.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property title
 */
@JsonClass(generateAdapter = true)
data class PatchRecipeRequestParameter(
    @Json(name = "title") @field:Json(name = "title") var title: String
)
