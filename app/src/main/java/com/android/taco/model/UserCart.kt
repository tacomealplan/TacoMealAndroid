package com.android.taco.model

data class UserCart (
    val id : String,
    val createDate : String,
    val isChecked : Boolean,
    val materialId : String,
    val materialName : String,
    val recipeId : String,
    val userId : String,
) {
    companion object{
        fun newInstance(data: MutableMap<String, Any>): UserCart{
            val cart = UserCart(
                id = data["id"].toString(),
                createDate = data["createDate"].toString(),
                isChecked = data["isChecked"].toString().toBoolean(),
                materialId = data["materialId"].toString(),
                materialName = data["materialName"].toString(),
                recipeId = data["recipeId"].toString(),
                userId = data["userId"].toString()
            )
            return cart
        }
    }
}