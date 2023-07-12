package com.android.taco.ui.main.views.chef.plan

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.model.Plan
import com.android.taco.model.Recipe
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(

) : ViewModel(){
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isLoading = mutableStateOf(false)
    var plan = mutableStateOf<Plan?>(null)


    fun getPlanById(planId : String ){
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
}