package com.example.swcook.front.validation

import com.example.swcook.front.models.PatchRecipeRequestParameter
import com.example.swcook.front.models.PostRecipeRequest
import com.example.swcook.front.models.PostRecipeRequestRecipe
import org.valiktor.ConstraintViolationException
import org.valiktor.functions.isGreaterThanOrEqualTo
import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isNotNull
import org.valiktor.functions.validate
import org.valiktor.validate

@Throws(ConstraintViolationException::class)
fun PostRecipeRequest.validate() {
    validate(this) {
        validate(PostRecipeRequest::recipe).isNotNull()
        validate(PostRecipeRequest::recipe).validate {
            validate(PostRecipeRequestRecipe::title).isNotNull()
            validate(PostRecipeRequestRecipe::title).isNotBlank()

            validate(PostRecipeRequestRecipe::description).isNotNull()

            validate(PostRecipeRequestRecipe::cookingTime).isNotNull()
            validate(PostRecipeRequestRecipe::cookingTime).isGreaterThanOrEqualTo(0)
        }
    }
}

@Throws(ConstraintViolationException::class)
fun PatchRecipeRequestParameter.validate() {
    validate(this) {
        validate(PatchRecipeRequestParameter::title).isNotNull()
        validate(PatchRecipeRequestParameter::title).isNotBlank()
    }
}
