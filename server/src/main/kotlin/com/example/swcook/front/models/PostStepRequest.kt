/**
 * NOTE: This class is auto generated by the Swagger Gradle Codegen for the following API: SWCook API
 *
 * More info on this tool is available on https://github.com/Yelp/swagger-gradle-codegen
 */

package com.example.swcook.front.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @property position
 * @property description
 * @property prepTime
 */
@JsonClass(generateAdapter = true)
data class PostStepRequest(
    @Json(name = "position") @field:Json(name = "position") var position: Int,
    @Json(name = "description") @field:Json(name = "description") var description: String,
    @Json(name = "prep_time") @field:Json(name = "prep_time") var prepTime: Int
)
