package com.android.taco.ui.main.views.favourites

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.model.Recipe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel @Inject constructor(
): ViewModel() {
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isLoading = mutableStateOf(false)
    var favouritesRecipes = mutableListOf<Recipe>()
    init {
        getFavouritesRecipeIds()
    }

    private fun getFavouritesRecipeIds(){
        isLoading.value = true
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        firestore.collection("UserRecipeLike")
            .whereEqualTo("UserId", currentUserId)
            .get()
            .addOnSuccessListener {
                val recipeIdList = ArrayList<String>()
                val data = it.documents
                data.forEach { item->
                    recipeIdList.add(item.data?.get("RecipeId").toString())
                }
                getFavouriteRecipes(recipeIdList)
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }

    private fun getFavouriteRecipes(recipeIdList : List<String>){
        if(recipeIdList.isEmpty()){
            isLoading.value = false
            return
        }
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
                favouritesRecipes.addAll(recipes)
                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }




}