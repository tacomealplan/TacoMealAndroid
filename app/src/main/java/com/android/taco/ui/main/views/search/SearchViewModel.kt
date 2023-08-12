package com.android.taco.ui.main.views.search

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.model.Recipe
import com.android.taco.util.containsAny
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
): ViewModel() {
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isLoading = mutableStateOf(false)
    var searchResults = mutableListOf<Recipe>()
    var categories = mutableListOf<String>()
    var meals = mutableListOf<String>()
    var filterMeal = mutableStateOf<String?>(null)
    var filterCategories = mutableStateListOf<String>()
    init {
        getRecipeCategories()
        getRecipeMeals()
    }

    fun searchRecipeByText(searchText : String){
        isLoading.value = true
        firestore.collection("Recipe")
            .get()
            .addOnSuccessListener {
                val recipes = ArrayList<Recipe>()
                val data = it.documents
                data.forEach { item->
                    if(item.data != null){
                        val tempRecipe = Recipe.newInstance(item.data!!)
                        if(tempRecipe.name.lowercase().contains(searchText.lowercase())
                            && (filterCategories.isEmpty() || tempRecipe.categories.containsAny(filterCategories))
                            && (filterMeal.value.isNullOrEmpty() || tempRecipe.meal == filterMeal.value)
                        ){
                            recipes.add(tempRecipe)
                        }
                    }

                }
                searchResults.clear()
                searchResults.addAll(recipes)
                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }

    private fun getRecipeCategories(){
        firestore.collection("RecipeCategory")
            .get()
            .addOnSuccessListener {
                val result = ArrayList<String>()
                val data = it.documents
                data.forEach { item->
                    if(item.data != null)
                        result.add(item.data!!["Name"].toString())
                }
                categories.clear()
                categories.addAll(result)
                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }

    private fun getRecipeMeals(){
        firestore.collection("RecipeMeal")
            .get()
            .addOnSuccessListener {
                val result = ArrayList<String>()
                val data = it.documents
                data.forEach { item->
                    if(item.data != null)
                        result.add(item.data!!["Name"].toString())
                }
                meals.clear()
                meals.addAll(result)
                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }



}