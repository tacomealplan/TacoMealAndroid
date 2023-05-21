package com.android.taco.model

data class ErrorResponse(
    val customError: Any,
    val description: Any,
    val error: Any,
    val errorMessage: Any,
    val errors: List<Any>,
    val type: Any
)