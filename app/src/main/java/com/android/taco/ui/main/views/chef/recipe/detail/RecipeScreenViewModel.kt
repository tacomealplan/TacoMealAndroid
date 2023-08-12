package com.android.taco.ui.main.views.chef.recipe.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.model.Recipe
import com.android.taco.model.RecipeDetail
import com.android.taco.ui.main.views.chef.recipe.getUrlForStorage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeScreenViewModel @Inject constructor(

) : ViewModel() {
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isLoading = mutableStateOf(false)
    var hasError = mutableStateOf(false)
    var recipe = mutableStateOf<Recipe?>(null)
    var isEditEnabled = mutableStateOf(false)
    var recipeDetail = mutableStateOf<RecipeDetail?>(null)
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
                    getUrlForStorage(recipe.value?.coverPhotoLink ?: ""){url ->
                        coverPhotoUrl.value = url
                    }
                    getRecipeDetailById(recipeId)
                    isEditEnabled.value = (FirebaseAuth.getInstance().currentUser?.uid
                        ?: "") == recipe.value!!.createdBy
                }
                else
                    hasError.value = true

            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }

    fun deleteRecipe(recipeId : String, onSuccess: () -> Unit, onError: () -> Unit){
        isLoading.value = true
        firestore.collection("Recipe").document(recipeId)
            .delete()
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                isLoading.value = false
                onError.invoke()
            }
    }
    private fun getRecipeDetailById(recipeId : String ){
        firestore.collection("RecipeDetail")
            .whereEqualTo("recipeId",recipeId)
            .get()
            .addOnSuccessListener {
                val recipeDetails = ArrayList<RecipeDetail>()
                val data = it.documents
                data.forEach { item->
                    if(item.data != null)
                        recipeDetails.add(RecipeDetail.newInstance(item.data!!))
                }
                if(recipeDetails.isNotEmpty())
                    recipeDetail.value = recipeDetails.first()
                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }

}