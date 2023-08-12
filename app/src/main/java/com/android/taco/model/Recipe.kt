package com.android.taco.model

import java.util.UUID

data class Recipe(
    val id: String,
    var meal: String,
    var name: String,
    var personCount: Int,
    var categories: ArrayList<String>,
    var coverPhotoLink: String,
    var createDate: String,
    var createdBy: String,
    var creatorPhotoURL: String,
    var creatorUserName: String,
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
                categories = data["categories"] as ArrayList<String>,
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

        fun toHashMap(recipe: Recipe) : HashMap<String,Any>{
            val item = HashMap<String,Any>()
            item["id"] = recipe.id
            item["meal"] = recipe.meal
            item["name"] = recipe.name
            item["personCount"] = recipe.personCount
            item["duration"] = recipe.duration
            item["description"] = recipe.description
            item["creatorUserName"] = recipe.creatorUserName
            item["creatorPhotoURL"] = recipe.creatorPhotoURL
            item["createdBy"] = recipe.createdBy
            item["createDate"] = recipe.createDate
            item["coverPhotoLink"] = recipe.coverPhotoLink
            item["categories"] = recipe.categories

            return item
        }
    }
}