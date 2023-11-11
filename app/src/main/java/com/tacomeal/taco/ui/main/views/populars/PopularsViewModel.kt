package com.tacomeal.taco.ui.main.views.populars

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tacomeal.taco.model.Recipe
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PopularsViewModel @Inject constructor(
): ViewModel() {
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isLoading = mutableStateOf(false)
    var popularRecipes = mutableListOf<Recipe>()
    init {
        getPopularRecipeIds()
    }

    private fun getPopularRecipeIds(forWidget : Boolean = false){
        isLoading.value = true
        firestore.collection("PopularRecipes")
            .get()
            .addOnSuccessListener {
                val recipeIdList = ArrayList<String>()
                val data = it.documents
                data.forEach { item->
                    recipeIdList.add(item.data?.get("RecipeId").toString())
                }
                getPopularRecipes(recipeIdList)
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }

    private fun getPopularRecipes(recipeIdList : List<String>){
        firestore.collection("Recipe")
            .whereIn("id",recipeIdList)
            .get()
            .addOnSuccessListener {
                val recipes = ArrayList<Recipe>()
                val data = it.documents
                data.forEach { item->
                    if(item.data != null)
                        recipes.add(Recipe.newInstance(item.data!!))
                }
                popularRecipes.addAll(recipes)
                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }




}