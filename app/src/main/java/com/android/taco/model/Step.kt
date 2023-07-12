package com.android.taco.model

data class Step(
    val description: String,
    val id: String,
    var index: Int,
    val photoLink: String
)