package com.android.taco.ui.main.views.chef.plan.edit

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.model.Plan
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlanEditViewModel @Inject constructor(

) : ViewModel(){
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isLoading = mutableStateOf(false)
    var isNewInstance = mutableStateOf(true)
    var plan = mutableStateOf<Plan?>(null)
    var allCategories = mutableListOf<String>()
    var selectedCategories = mutableStateListOf<String>()
    var planName = mutableStateOf("")
    var planMotivation = mutableStateOf("")

    init {
        getRecipeCategories()
    }


    fun getPlanById(planId : String){
        isNewInstance.value = false
        firestore.collection("Plan")
            .whereEqualTo("Id",planId)
            .get()
            .addOnSuccessListener {
                val plans = ArrayList<Plan>()
                val data = it.documents
                data.forEach { item->
                    if(item.data != null)
                        plans.add(Plan.newInstance(item.data!!))
                }
                if(plans.isNotEmpty())
                    plan.value = plans.first()
                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }

    fun createNewInstance(){
        isNewInstance.value = true
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
                allCategories.clear()
                allCategories.addAll(result)
                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }
}