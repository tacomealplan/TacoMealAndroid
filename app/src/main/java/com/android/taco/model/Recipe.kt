package com.android.taco.model

import java.util.UUID

data class Recipe(
    val id: String,
    val meal: String,
    var name: String,
    var personCount: Int,
    val categories: ArrayList<String>,
    val coverPhotoLink: String,
    val createDate: String,
    val createdBy: String,
    val creatorPhotoURL: String,
    val creatorUserName: String,
    var description: String,
    var duration: Int,
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

        fun createNewInstance():Recipe{
            val recipe = Recipe(
                id = UUID.randomUUID().toString(),
                meal = "",
                name = "",
                personCount = 0,
                categories = ArrayList<String>(),
                coverPhotoLink = "",
                createDate = "",
                createdBy = "",
                creatorPhotoURL = "",
                creatorUserName = "",
                description = "",
                duration = 0
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