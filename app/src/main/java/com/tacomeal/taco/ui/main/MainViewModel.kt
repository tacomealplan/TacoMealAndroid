package com.tacomeal.taco.ui.main

import androidx.lifecycle.ViewModel
import com.tacomeal.taco.repository.ApiRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ApiRepository
): ViewModel() {
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        getRecipeCategories()
        getRecipeMeals()
    }

    private fun getRecipeCategories(){
        firestore.collection("RecipeCategory")
            .get()
            .addOnSuccessListener {
                val categories = ArrayList<String>()
                val data = it.documents
                data.forEach { item->
                    categories.add(item.data?.get("Name").toString())
                }
            }
            .addOnFailureListener{
                it.printStackTrace()
            }
    }

    private fun getRecipeMeals(){
        firestore.collection("RecipeMeal")
            .get()
            .addOnSuccessListener {
                val meals = ArrayList<String>()
                val data = it.documents
                data.forEach { item->
                    meals.add(item.data?.get("Name").toString())
                }
            }
            .addOnFailureListener{
                it.printStackTrace()
            }
    }
}