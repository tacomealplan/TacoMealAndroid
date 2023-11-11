package com.tacomeal.taco.model

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.lang.reflect.Type

data class RecipeDetail(
    val id: String,
    val recipeId: String,
    var materials: ArrayList<Material>,
    var steps: ArrayList<Step>
) {

    companion object{
        fun newInstance(data: MutableMap<String, Any>): RecipeDetail{
            val materialListType: Type = object : TypeToken<ArrayList<Material>>() {}.type
            val stepListType: Type = object : TypeToken<ArrayList<Step>>() {}.type
            val recipeDetail = RecipeDetail(
                id = data["id"].toString(),
                recipeId = data["recipeId"].toString(),
                materials = Gson().fromJson(data["materials"].toString(),materialListType ),
                steps = Gson().fromJson(data["steps"].toString(),stepListType ),
            )
            return recipeDetail
        }
    }
}

fun <T> Gson.fromJsonList(jsonString: String): ArrayList<T> =
    this.fromJson(jsonString, object: TypeToken<ArrayList<T>>() { }.type)
