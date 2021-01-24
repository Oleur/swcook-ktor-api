package com.example.swcook.front.validation

import com.example.swcook.front.models.AddIngredientToRecipeRequest
import com.example.swcook.front.models.PostIngredientRequest
import org.valiktor.ConstraintViolationException
import org.valiktor.functions.doesNotContain
import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.validate

@Throws(ConstraintViolationException::class)
fun PostIngredientRequest.validate() {
    validate(this) {
        validate(PostIngredientRequest::name).isNotNull()
        validate(PostIngredientRequest::name).isNotBlank()
    }
}

@Throws(ConstraintViolationException::class)
fun AddIngredientToRecipeRequest.validate() {
    validate(this) {
        validate(AddIngredientToRecipeRequest::ingredients).isNotNull()
        validate(AddIngredientToRecipeRequest::ingredients).isNotEmpty()
        validate(AddIngredientToRecipeRequest::ingredients).doesNotContain(null)
    }
}
