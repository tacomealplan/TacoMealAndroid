package com.tacomeal.taco.ui.main.views.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tacomeal.taco.model.Plan
import com.tacomeal.taco.model.Recipe
import com.tacomeal.taco.model.RecipeDetail
import com.tacomeal.taco.model.UserPlan
import com.tacomeal.taco.repository.ApiRepository
import com.tacomeal.taco.ui.main.views.chef.recipe.getUrlForStorage
import com.tacomeal.taco.util.Resource
import com.tacomeal.taco.util.getCurrentWeekOfYear
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor():ViewModel() {
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isLoading = mutableStateOf(false)
    var hasError = mutableStateOf(false)
    var hasNoPlan = mutableStateOf(false)
    var recipe = mutableStateOf<Recipe?>(null)
    var activePlan = mutableStateOf<UserPlan?>(null)


    fun getActivePlan(){
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val weekOfYear = getCurrentWeekOfYear()
        firestore.collection("UserPlan")
            .whereEqualTo("UserId",userId)
            .get()
            .addOnSuccessListener {
                val data = it.documents
                data.forEach { item->
                    if(item.data != null){
                        val plan = UserPlan.newInstance(item.data!!)
                        if(plan.isActive && plan.weekOfYear.toInt() == weekOfYear){
                            this.activePlan.value = plan
                            return@forEach
                        }
                    }
                }
            }
            .addOnFailureListener{
                it.printStackTrace()
            }
    }

    fun getMyPlans(){
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            firestore.collection("Plan")
                .whereEqualTo("CreatedBy",it)
                .get()
                .addOnSuccessListener {
                    val data = it.documents
                    hasNoPlan.value = data.isEmpty()
                }
                .addOnFailureListener{
                    hasNoPlan.value = true
                }
        }


    }
}