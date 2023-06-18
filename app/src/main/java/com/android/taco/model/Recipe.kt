package com.android.taco.model

data class Recipe(
    val id: String,
    val meal: String,
    val name: String,
    val personCount: Int,
    val categories: ArrayList<String>,
    val coverPhotoLink: String,
    val createDate: String,
    val createdBy: String,
    val creatorPhotoURL: String,
    val creatorUserName: String,
    val description: String,
    val duration: Int,
) {
    companion object{

        fun newInstance(data: MutableMap<String, Any>): Recipe{
            val recipe = Recipe(
                id = data["id"].toString(),
                meal = data["meal"].toString(),
                name = data["name"].toString(),
                personCount = data["personCount"].toString().toInt(),
                categories = ArrayList<String>(),
                coverPhotoLink = data["coverPhotoLink"].toString(),
                createDate = data["createDate"].toString(),
                createdBy = data["createdBy"].toString(),
                creatorPhotoURL = data["creatorPhotoURL"].toString(),
                creatorUserName = data["creatorUserName"].toString(),
                description = data["description"].toString(),
                duration = data["duration"].toString().toInt()
            )
            return recipe
        }

        fun dummyInstance(): Recipe{
            val recipe = Recipe(
                id = "123456",
                meal = "Öğün",
                name = "Yemek",
                personCount = 1,
                categories = ArrayList<String>(),
                coverPhotoLink = "",
                createDate = "",
                createdBy = "",
                creatorPhotoURL = "",
                creatorUserName = "Ahmet",
                description = "Açıklama",
                duration = 60
            )
            return recipe
        }
    }
}