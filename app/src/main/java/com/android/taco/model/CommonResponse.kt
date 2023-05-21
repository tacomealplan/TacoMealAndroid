package com.android.taco.model

data class CommonResponse(
    val code: Int,
    val `data`: Any,
    val errorResponse: ErrorResponse,
    val infoMessage: String
)