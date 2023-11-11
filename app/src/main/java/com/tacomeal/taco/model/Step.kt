package com.tacomeal.taco.model

data class Step(
    val description: String,
    val id: String,
    var index: Int,
    val photoLink: String
)