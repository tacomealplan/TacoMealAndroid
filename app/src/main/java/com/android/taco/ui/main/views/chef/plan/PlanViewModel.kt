package com.android.taco.ui.main.views.chef.plan

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.model.Plan
import com.android.taco.model.Recipe
import com.android.taco.model.UserPlan
import com.android.taco.ui.main.views.chef.recipe.getUrlForStorage
import com.android.taco.util.getCurrentWeekOfYear
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(

) : ViewModel(){
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isLoading = mutableStateOf(false)
    var plan = mutableStateOf<Plan?>(null)
    var dailyRecipes = mutableStateListOf<Recipe>()
    var activePlan = mutableStateOf<UserPlan?>(null)


    fun getPlanById(planId : String ){
        isLoading.value = true
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

    fun getActivePlan(){
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val weekOfYear = getCurrentWeekOfYear()
        firestore.collection("UserPlan")
            .whereEqualTo("UserId",userId)
            //.whereEqualTo("isActive", true)
            //.whereEqualTo("WeekOfYear",weekOfYear )
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

    fun getDailyRecipes(planId: String){
        isLoading.value = true
        firestore.collection("Plan")
            .whereEqualTo("Id",planId)
            .get()
            .addOnSuccessListener {
                val plans = ArrayList<Plan>()
                val dailyRecipeIdList = ArrayList<String>()

                val data = it.documents
                data.forEach { item->
                    if(item.data != null)
                        plans.add(Plan.newInstance(item.data!!))
                }
                if(plans.isNotEmpty()){
                    val plan = plans.first()
                    val dailyPlan = plan.getDay(Day.getCurrentDay())
                    dailyRecipeIdList.addAll(dailyPlan.breakfast)
                    dailyRecipeIdList.addAll(dailyPlan.lunch)
                    dailyRecipeIdList.addAll(dailyPlan.dinner)
                    getRecipes(dailyRecipeIdList)
                }
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }

    private fun getRecipes(dailyRecipeIdList : ArrayList<String>){
        if(dailyRecipeIdList.isEmpty()) {
            isLoading.value = false
            return
        }
        firestore.collection("Recipe")
            .whereIn("id", dailyRecipeIdList)
            .get()
            .addOnSuccessListener { snapshot ->
                val recipes = ArrayList<Recipe>()
                val data = snapshot.documents
                data.forEach { item->
                    if(item.data != null)
                        recipes.add(Recipe.newInstance(item.data!!))
                }
                dailyRecipeIdList.forEach { id->
                    dailyRecipes.add(recipes.first { it.id == id })
                }
                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }
}