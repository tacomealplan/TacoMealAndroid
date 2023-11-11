package com.tacomeal.taco.ui.main.views.chef.recipe.edit

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tacomeal.taco.model.Material
import com.tacomeal.taco.model.Recipe
import com.tacomeal.taco.model.RecipeDetail
import com.tacomeal.taco.model.Step
import com.tacomeal.taco.ui.main.views.chef.recipe.getUrlForStorage
import com.tacomeal.taco.util.now
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
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
    var recipe = mutableStateOf<Recipe?>(null)
    var recipeDetail = mutableStateOf<RecipeDetail?>(null)
    var allMeals = mutableListOf<String>()

    var selectedCategories = mutableStateListOf<String>()
    var recipeMeal = mutableStateOf("")
    var recipeName = mutableStateOf("")
    var recipeDesc = mutableStateOf("")
    var recipePersonCount = mutableStateOf<Int?>(null)
    var recipeDuration = mutableStateOf<Int?>(null)

    private val materials = MutableStateFlow<List<Material>>(listOf())
    private val steps = MutableStateFlow<List<Step>>(listOf())
    val materialsState = materials.asStateFlow()
    val stepsState = steps.asStateFlow()

    init {
        getRecipeCategories()
        getRecipeMeals()
    }

    fun getRecipeInfo(recipeId : String){
        isNewInstance.value = false
        isLoading.value = true
        firestore.collection("Recipe")
            .whereEqualTo("id",recipeId)
            .get()
            .addOnSuccessListener {
                val recipes = ArrayList<Recipe>()
                val data = it.documents
                data.forEach { item->
                    if(item.data != null)
                        recipes.add(Recipe.newInstance(item.data!!))
                }
                if(recipes.isNotEmpty()){
                    recipe.value = recipes.first()
                    getRecipeDetailById(recipeId)
                }
                else{

                }
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }

    private fun getRecipeDetailById(recipeId : String ){
        firestore.collection("RecipeDetail")
            .whereEqualTo("recipeId",recipeId)
            .get()
            .addOnSuccessListener {
                val recipeDetails = ArrayList<RecipeDetail>()
                val data = it.documents
                data.forEach { item->
                    if(item.data != null)
                        recipeDetails.add(RecipeDetail.newInstance(item.data!!))
                }
                if(recipeDetails.isNotEmpty())
                    recipeDetail.value = recipeDetails.first()
                setUIStates()
                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }
    private fun setUIStates(){
        recipeName.value = recipe.value!!.name
        recipeDesc.value = recipe.value!!.description
        recipePersonCount.value = recipe.value!!.personCount
        recipeDuration.value = recipe.value!!.duration
        recipeMeal.value = recipe.value!!.meal
        selectedCategories.clear()
        selectedCategories.addAll(recipe.value!!.categories)
        materials.value = recipeDetail.value!!.materials
        steps.value = recipeDetail.value!!.steps
    }

    fun createNewRecipe(){
        isNewInstance.value = true
        recipe.value = Recipe.createNewInstance()
    }

    private fun isRecipeValid() : String{
        if(recipeName.value.isEmpty() || recipeName.value.count() < 3 || recipeName.value.count() > 30){
            return "Tarif adı en az 3 en fazla 30 karakter olmalıdır"
        }
        if(recipeDesc.value.isEmpty() || recipeDesc.value.count() < 3 ||recipeDesc.value.count() > 200){
            return "Tarif açıklaması en az 3 en fazla 200 karakter olmalıdır"
        }
        if(recipeDuration.value == null || recipeDuration.value!! < 1){
            return "Tarif süresi en az 1 dk olabilir"
        }
        if(recipePersonCount.value == null || recipePersonCount.value!! < 1 ||recipePersonCount.value!! > 100){
            return "Kişi sayısı en az 1 en fazla 100 olabilir"
        }
        if(recipeMeal.value.isEmpty()){
            return "Öğün seçmelisiniz"
        }
        if(selectedCategories.isEmpty()){
            return "En az 1 kategori seçmelisiniz"
        }

        if(steps.value.isEmpty()){
            return "En az 1 adım girmelisiniz"
        }
        if(materials.value.isEmpty()){
            return "En az 1 malzeme girmelisiniz"
        }

        if(steps.value.none { it.photoLink.isNotEmpty() }){
            return "En az 1 adım için fotoğraf yüklemelisiniz"
        }
        return ""
    }

    fun upsertRecipe(onSuccess : () -> Unit, onError : () -> Unit, validationFailed : (message : String) -> Unit){
        if(isRecipeValid().isNotEmpty()) {
            validationFailed.invoke(isRecipeValid())
            return
        }
        isLoading.value = true
        val currentUser = FirebaseAuth.getInstance().currentUser ?: return
        if(isNewInstance.value){
            recipe.value!!.createdBy = currentUser.uid
            recipe.value!!.creatorUserName = currentUser.displayName.toString()
            recipe.value!!.createDate = now()
            recipe.value!!.creatorPhotoURL = currentUser.photoUrl.toString()
        }
        recipe.value!!.name = recipeName.value
        recipe.value!!.description = recipeDesc.value
        recipe.value!!.personCount = recipePersonCount.value!!
        recipe.value!!.duration = recipeDuration.value!!
        recipe.value!!.meal = recipeMeal.value
        recipe.value!!.categories = ArrayList(selectedCategories.toList())
        recipeDetail.value = RecipeDetail(id= if(isNewInstance.value) UUID.randomUUID().toString() else recipeDetail.value!!.id,
            recipeId = recipe.value!!.id,
            materials = ArrayList(materials.value),
            steps = ArrayList(steps.value)
        )
        recipeDetail.value!!.materials =  ArrayList(materials.value)
        recipeDetail.value!!.steps = ArrayList(steps.value)
        recipe.value!!.coverPhotoLink = try {
            recipeDetail.value!!.steps.last { it.photoLink.isNotEmpty() }.photoLink
        } catch (e: Exception) {
            ""
        }
        firestore.collection("Recipe").document(recipe.value!!.id)
            .set(Recipe.toHashMap(recipe.value!!))
            .addOnSuccessListener {
                insertRecipeDetail(onSuccess = {
                    isLoading.value = false
                    onSuccess.invoke()
                }){
                    isLoading.value = false
                    onError.invoke()
                }
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
                onError.invoke()
            }
    }

    private fun insertRecipeDetail(onSuccess : () -> Unit, onError: () -> Unit){
        val item = HashMap<String,Any>()
        item["id"] = recipeDetail.value!!.id
        item["recipeId"] = recipeDetail.value!!.recipeId
        item["materials"] = Gson().toJson(recipeDetail.value!!.materials)
        item["steps"] = Gson().toJson(recipeDetail.value!!.steps)

        firestore.collection("RecipeDetail").document(recipeDetail.value!!.id)
            .set(item)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener{
                it.printStackTrace()
                onError.invoke()
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
