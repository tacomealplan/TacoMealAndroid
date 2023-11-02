package com.android.taco.ui.main.views.search

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.model.Recipe
import com.android.taco.util.containsAny
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
): ViewModel() {
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isLoading = mutableStateOf(false)
    var allRecipes = mutableListOf<Recipe>()
    var searchResults = mutableListOf<Recipe>()
    var categories = mutableListOf<String>()
    var searchText = mutableStateOf("")
    var meals = mutableListOf<String>()
    var filterMeal = mutableStateOf<String?>(null)
    var filterCategories = mutableStateListOf<String>()
    init {
        getRecipeCategories()
        getRecipeMeals()
    }

    fun getAllRecipes(){
        isLoading.value = true
        firestore.collection("Recipe")
            .get()
            .addOnSuccessListener {
                val recipes = ArrayList<Recipe>()
                val data = it.documents
                data.forEach { item->
                    if(item.data != null){
                        val tempRecipe = Recipe.newInstance(item.data!!)
                        recipes.add(tempRecipe)
                    }

                }
                allRecipes.clear()
                allRecipes.addAll(recipes)
                searchResults.clear()
                searchResults.addAll(recipes)
                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }

    fun searchRecipesByFilter(){
        isLoading.value = true
        val recipes = ArrayList<Recipe>()
        allRecipes.forEach {item->
            if(item.name.lowercase().contains(searchText.value.lowercase())
                && (filterCategories.isEmpty() || item.categories.containsAny(filterCategories))
                && (filterMeal.value.isNullOrEmpty() || item.meal == filterMeal.value)
            ){
                recipes.add(item)
            }
        }
        searchResults.clear()
        searchResults.addAll(recipes)
        isLoading.value = false
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