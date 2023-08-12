package com.android.taco.ui.main.views.chef.plan

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.model.Day
import com.android.taco.model.Plan
import com.android.taco.model.Recipe
import com.android.taco.model.UserPlan
import com.android.taco.ui.main.views.chef.recipe.getUrlForStorage
import com.android.taco.util.getCurrentWeekOfYear
import com.android.taco.util.now
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(

) : ViewModel(){
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isLoading = mutableStateOf(false)
    var plan = mutableStateOf<Plan?>(null)
    var planRecipes = mutableStateListOf<Recipe>()
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
                val planRecipeIdList = arrayListOf<String>()
                Day.values().forEach { day->
                    try {
                        val dailyPlan = plan.value!!.getDay(day)
                        planRecipeIdList.addAll(dailyPlan.breakfast)
                        planRecipeIdList.addAll(dailyPlan.lunch)
                        planRecipeIdList.addAll(dailyPlan.dinner)
                    } catch (e: Exception) {}
                }
                getPlanRecipes(planRecipeIdList)
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }

    fun deletePlan(planId : String, onSuccess: () -> Unit, onError: () -> Unit){
        isLoading.value = true
        firestore.collection("Plan").document(planId)
            .delete()
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                isLoading.value = false
                onError.invoke()
            }
    }

    private fun getPlanRecipes(planRecipeIdList : ArrayList<String>){
        if(planRecipeIdList.isEmpty()) {
            isLoading.value = false
            return
        }
        firestore.collection("Recipe")
            .whereIn("id", planRecipeIdList)
            .get()
            .addOnSuccessListener { snapshot ->
                val recipes = ArrayList<Recipe>()
                val data = snapshot.documents
                data.forEach { item->
                    if(item.data != null)
                        recipes.add(Recipe.newInstance(item.data!!))
                }
                planRecipeIdList.forEach { id->
                    planRecipes.add(recipes.first { it.id == id })
                }
                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }

    fun setActivePlan(planId: String, week : Int, onSuccess : () -> Unit, onError : () -> Unit){
        isLoading.value = true
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        val item = HashMap<String,Any>()
        item["CreateDate"] = now()
        item["PlanId"] = planId
        item["isActive"] = true
        item["WeekOfYear"] = getCurrentWeekOfYear() + week
        item["UserId"] = currentUserId.toString()
        firestore.collection("UserPlan")
            .add(item)
            .addOnSuccessListener {
                onSuccess.invoke()
                isLoading.value = false
            }
            .addOnFailureListener{
                onError.invoke()
                isLoading.value = false
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
                    dailyRecipeIdList.clear()
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
                dailyRecipes.clear()
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