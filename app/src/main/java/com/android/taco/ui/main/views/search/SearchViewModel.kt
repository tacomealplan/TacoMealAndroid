package com.android.taco.ui.main.views.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.model.Recipe
import com.android.taco.ui.main.views.chef.Meal
import com.google.firebase.auth.FirebaseAuth
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
    var filterMeal = mutableStateOf<Meal?>(null)
    init {
        getRecipeCategories()
    }

    fun searchRecipeByText(searchText : String){
        /*if(!filterMeal.isEmpty){
            query = collectionRef
                .whereField("meal", isEqualTo: filterMeal)
        }

        if(!filterCategories.isEmpty){
            query = collectionRef
                .whereField("categories", arrayContainsAny: self.filterCategories)
        }*/
        isLoading.value = true
        firestore.collection("Recipe")
            .where(Filter.greaterThanOrEqualTo("name",searchText))
            .where(Filter.lessThan("name", searchText+"z"))
            .get()
            .addOnSuccessListener {
                val recipes = ArrayList<Recipe>()
                val data = it.documents
                data.forEach { item->
                    if(item.data != null)
                        recipes.add(Recipe.newInstance(item.data!!))
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



}