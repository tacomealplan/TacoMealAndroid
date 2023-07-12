package com.android.taco.ui.main.views.chef.recipe.edit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.model.Material
import com.android.taco.model.Recipe
import com.android.taco.model.RecipeDetail
import com.android.taco.model.Step
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditRecipeViewModel @Inject constructor() : ViewModel() {
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isLoading = mutableStateOf(false)
    var isNewInstance = mutableStateOf(true)
    var allCategories = mutableListOf<String>()
    var allMeals = mutableListOf<String>()
    var selectedCategories = mutableListOf<String>()

    var recipe = mutableStateOf<Recipe?>(null)
    var recipeDetail = mutableStateOf<RecipeDetail?>(null)

    private val materials = MutableStateFlow<List<Material>>(listOf())
    private val steps = MutableStateFlow<List<Step>>(listOf())
    val materialsState = materials.asStateFlow()
    val stepsState = steps.asStateFlow()

    init {
        getRecipeCategories()
        materials.value = listOf()
        steps.value = listOf()
    }

    fun getRecipeInfo(){
        isNewInstance.value = false
    }

    fun createNewRecipe(){
        recipe.value = Recipe.createNewInstance()
    }

    fun addNewRecipe(){

    }

    fun updateRecipe(){

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
                allMeals.clear()
                allMeals.addAll(result)
                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }

    fun swapMaterials(from: Int, to: Int) {
        val fromItem = materials.value[from]
        val toItem = materials.value[to]
        val newList = materials.value.toMutableList()
        newList[from] = toItem
        newList[to] = fromItem

        materials.value = newList
    }

    fun addMaterial(material: Material) {
        val newList = materials.value.toMutableList()
        newList.add(material)
        materials.value = newList
    }
    fun removeMaterial(material: Material) {
        val newList = materials.value.toMutableList()
        newList.remove(material)
        materials.value = newList
    }

    fun swapSteps(from: Int, to: Int) {
        val fromItem = steps.value[from]
        val toItem = steps.value[to]
        val newList = steps.value.toMutableList()
        newList[from] = toItem
        newList[to] = fromItem
        newList.forEachIndexed { index, step ->
            step.index = index+1
        }
        steps.value = newList
    }

    fun addStep(step: Step) {
        val newList = steps.value.toMutableList()
        newList.add(step)
        newList.forEachIndexed { index, step ->
            step.index = index+1
        }
        steps.value = newList
    }

    fun removeStep(step: Step) {
        val newList = steps.value.toMutableList()
        newList.remove(step)
        newList.forEachIndexed { index, step ->
            step.index = index+1
        }
        steps.value = newList
    }
}
