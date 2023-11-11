package com.tacomeal.taco.ui.main.views.chef.recipe

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tacomeal.taco.model.Recipe
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeByIdViewModel @Inject constructor(

) : ViewModel(){
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isLoading = mutableStateOf(false)
    var recipe = mutableStateOf<Recipe?>(null)
    var coverPhotoUrl = mutableStateOf("")


    fun getRecipeById(recipeId : String ){
        firestore.collection("Recipe")
            .whereEqualTo("id",recipeId)
            .get()
            .addOnSuccessListener {
                val recipes = ArrayList<Recipe>()
                val data = it.documents
                data.forEach { item->
                    if(item.data != null)
                        recipes.add(Recipe.newInstance(item.data!!))
                }
                if(recipes.isNotEmpty()){
                    recipe.value = recipes.first()
                    getUrlForStorage(recipe.value!!.coverPhotoLink ?: ""){url->
                        coverPhotoUrl.value = url
                    }
                }

                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }
}