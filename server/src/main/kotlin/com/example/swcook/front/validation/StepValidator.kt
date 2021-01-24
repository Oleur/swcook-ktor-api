package com.example.swcook.front.validation

import com.example.swcook.front.models.PostStepRequest
import org.valiktor.ConstraintViolationException
import org.valiktor.functions.isGreaterThanOrEqualTo
import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isNotNull
import org.valiktor.validate

@Throws(ConstraintViolationException::class)
fun PostStepRequest.validate() {
    validate(this) {
        validate(PostStepRequest::position).isNotNull()
        validate(PostStepRequest::position).isGreaterThanOrEqualTo(0)

        validate(PostStepRequest::description).isNotNull()
        validate(PostStepRequest::description).isNotBlank()

        validate(PostStepRequest::prepTime).isNotNull()
        validate(PostStepRequest::prepTime).isGreaterThanOrEqualTo(0)
    }
}
