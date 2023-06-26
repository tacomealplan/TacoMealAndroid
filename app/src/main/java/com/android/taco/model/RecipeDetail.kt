package com.android.taco.model

import com.google.common.reflect.TypeToken
import com.google.gson.Gson

data class RecipeDetail(
    val id: String,
    val recipeId: String,
    val materials: ArrayList<Material>,
    val steps: ArrayList<Step>
) {

    companion object{
        fun newInstance(data: MutableMap<String, Any>): RecipeDetail{
            val recipeDetail = RecipeDetail(
                id = data["id"].toString(),
                recipeId = data["recipeId"].toString(),
                materials = Gson().fromJsonList<Material>(data["materials"].toString()),
                steps = Gson().fromJsonList<Step>(data["steps"].toString())
            )
            return recipeDetail
        }
    }
}

fun <T> Gson.fromJsonList(jsonString: String): ArrayList<T> =
    this.fromJson(jsonString, object: TypeToken<ArrayList<T>>() { }.type)
