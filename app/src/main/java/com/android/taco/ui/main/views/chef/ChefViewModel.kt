package com.android.taco.ui.main.views.chef

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.model.Plan
import com.android.taco.model.Recipe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChefViewModel @Inject constructor(
): ViewModel(){
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isLoading = mutableStateOf(false)
    var myRecipes = mutableListOf<Recipe>()
    var myPlans = mutableListOf<Plan>()

    fun getMyRecipes(){
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            firestore.collection("Recipe")
                .whereEqualTo("createdBy",it)
                .get()
                .addOnSuccessListener {
                    val recipes = ArrayList<Recipe>()
                    val data = it.documents
                    data.forEach { item->
                        if(item.data != null)
                            recipes.add(Recipe.newInstance(item.data!!))
                    }
                    myRecipes.clear()
                    myRecipes.addAll(recipes)
                    isLoading.value = false
                }
                .addOnFailureListener{
                    isLoading.value = false
                }
        }


    }

    fun getMyPlans(){
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            firestore.collection("Plan")
                .whereEqualTo("CreatedBy",it)
                .get()
                .addOnSuccessListener {
                    val plans = ArrayList<Plan>()
                    val data = it.documents
                    data.forEach { item->
                        if(item.data != null)
                            plans.add(Plan.newInstance(item.data!!))
                    }
                    myPlans.clear()
                    myPlans.addAll(plans)
                    isLoading.value = false
                }
                .addOnFailureListener{
                    isLoading.value = false
                }
        }


    }
}