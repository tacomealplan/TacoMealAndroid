package com.android.taco.ui.main.views.chef.recipe.detail

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.model.Material
import com.android.taco.model.Recipe
import com.android.taco.model.RecipeDetail
import com.android.taco.model.UserCart
import com.android.taco.ui.main.views.chef.recipe.getUrlForStorage
import com.android.taco.util.getDate
import com.android.taco.util.now
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RecipeScreenViewModel @Inject constructor(

) : ViewModel() {
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var screenLoading = mutableStateOf(false)
    var hasError = mutableStateOf(false)
    var recipe = mutableStateOf<Recipe?>(null)
    var isEditEnabled = mutableStateOf(false)
    var recipeDetail = mutableStateOf<RecipeDetail?>(null)
    var coverPhotoUrl = mutableStateOf("")
    var userRecipeCartItems = mutableStateListOf<UserCart>()

    fun getRecipeById(recipeId : String ){
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
                    getUrlForStorage(recipe.value?.coverPhotoLink ?: ""){url ->
                        coverPhotoUrl.value = url
                    }
                    getUserCartOfRecipes()
                    getRecipeDetailById(recipeId)
                    isEditEnabled.value = (FirebaseAuth.getInstance().currentUser?.uid
                        ?: "") == recipe.value!!.createdBy
                }
                else
                    hasError.value = true

            }
            .addOnFailureListener{
                it.printStackTrace()
                screenLoading.value = false
            }
    }
    fun deleteRecipe(recipeId : String, onSuccess: () -> Unit, onError: () -> Unit){
        screenLoading.value = true
        firestore.collection("Recipe").document(recipeId)
            .delete()
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                screenLoading.value = false
                onError.invoke()
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
            }
            .addOnFailureListener{
                it.printStackTrace()
            }
    }

    private fun getUserCartOfRecipes(){
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        firestore.collection("UserCart")
            .whereEqualTo("userId", currentUserId)
            .get()
            .addOnSuccessListener {
                val cartItems = ArrayList<UserCart>()
                val data = it.documents
                data.forEach { item->
                    val cartItem = UserCart.newInstance(item.data!!)
                    var exclude = false
                    val update = getDate(cartItem.updateDate)
                    update?.let {date ->
                        if(cartItem.isChecked && Calendar.getInstance().time.time - update.time > 1000*60*60*24)
                            exclude = true
                    }
                    if(!exclude && cartItem.recipeId == recipe.value?.id)
                        cartItems.add(cartItem)
                }
                userRecipeCartItems.clear()
                userRecipeCartItems.addAll(cartItems)
            }
            .addOnFailureListener{
                it.printStackTrace()
            }
    }

    fun addUserCartItem(material: Material){
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        val item = HashMap<String,Any>()
        item["id"] = UUID.randomUUID().toString()
        item["createDate"] = now()
        item["updateDate"] = now()
        item["isChecked"] = false
        item["materialId"] = material.id
        item["materialName"] = material.name
        item["recipeId"] = recipe.value?.id.toString()
        item["userId"] = currentUserId.toString()
        firestore.collection("UserCart")
            .add(item)
            .addOnSuccessListener {
                getUserCartOfRecipes()
            }
            .addOnFailureListener{
                it.printStackTrace()
                //isLoading.value = false
            }
    }

}