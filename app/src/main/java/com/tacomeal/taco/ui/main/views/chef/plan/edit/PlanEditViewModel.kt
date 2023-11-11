package com.tacomeal.taco.ui.main.views.chef.plan.edit

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tacomeal.taco.model.Day
import com.tacomeal.taco.model.Meal
import com.tacomeal.taco.model.Plan
import com.tacomeal.taco.model.PlanDay
import com.tacomeal.taco.model.Recipe
import com.tacomeal.taco.util.now
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PlanEditViewModel @Inject constructor(

) : ViewModel(){
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isLoading = mutableStateOf(false)
    var isNewInstance = mutableStateOf(true)
    var plan = mutableStateOf<Plan?>(null)
    var planRecipes = mutableStateListOf<Recipe>()
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
                if(plans.isEmpty()){
                    isLoading.value = false
                    return@addOnSuccessListener
                }
                plan.value = plans.first()
                planName.value = plan.value?.name ?: ""
                planMotivation.value = plan.value?.motivation ?: ""
                selectedCategories.clear()
                plan.value?.categories?.let { it1 -> selectedCategories.addAll(it1) }
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

    fun addRecipeToPlan(recipe: Recipe, day: Day, meal: Meal){
        isLoading.value = true
        this.planRecipes.add(recipe)
        this.plan.value?.addRecipeToPlan(recipe,day, meal)
        isLoading.value = false
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

    fun createNewInstance(){
        isNewInstance.value = true
        plan.value = Plan(id = UUID.randomUUID().toString(), creatorProfileURL = "", creatorUserName = "", createdBy = "", motivation = "", name = "",
            mon = PlanDay(listOf(), listOf(), listOf()),
            tue = PlanDay(listOf(), listOf(), listOf()),
            wed = PlanDay(listOf(), listOf(), listOf()),
            thurs = PlanDay(listOf(), listOf(), listOf()),
            fri = PlanDay(listOf(), listOf(), listOf()),
            satur = PlanDay(listOf(), listOf(), listOf()),
            sun = PlanDay(listOf(), listOf(), listOf()),
            updateDate = "",
            createDate = "",
            categories = arrayListOf()
        )
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

    private fun isPlanValid() : String{
        if(planName.value.isEmpty() || planName.value.count() < 3 || planName.value.count() > 30){
            return "Plan adı en az 3 en fazla 30 karakter olmalıdır"
        }
        if(planMotivation.value.isEmpty() || planMotivation.value.count() < 3 ||planMotivation.value.count() > 200){
            return "Tarif motivasyonu en az 3 en fazla 200 karakter olmalıdır"
        }
        if(selectedCategories.isEmpty()){
            return "En az 1 kategori seçmelisiniz"
        }
        return ""
    }

    fun upsertPlan(onSuccess : () -> Unit, onError : () -> Unit, validationFailed : (message : String) -> Unit){
        if(isPlanValid().isNotEmpty()) {
            validationFailed.invoke(isPlanValid())
            return
        }
        isLoading.value = true
        val currentUser = FirebaseAuth.getInstance().currentUser ?: return
        if(isNewInstance.value){
            plan.value!!.createdBy = currentUser.uid
            plan.value!!.creatorUserName = currentUser.displayName.toString()
            plan.value!!.createDate = now()
        }
        plan.value!!.name = planName.value
        plan.value!!.motivation = planMotivation.value
        plan.value!!.categories = ArrayList(selectedCategories.toList())

        firestore.collection("Plan").document(plan.value!!.id)
            .set(Plan.toHashMap(plan.value!!))
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
                onError.invoke()
            }
    }
}