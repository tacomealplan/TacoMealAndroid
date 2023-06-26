package com.android.taco.ui.main.views.cart

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.model.Recipe
import com.android.taco.model.UserCart
import com.android.taco.repository.ApiRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
) :ViewModel() {
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var userCartItems = mutableListOf<UserCart>()



    fun getUserCartItems(){
        //todo:24 saat önce tiklenmişleri getirme
        isLoading.value = true
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        firestore.collection("UserCart")
            .whereEqualTo("userId", currentUserId)
            .get()
            .addOnSuccessListener {
                val cartItems = ArrayList<UserCart>()
                val data = it.documents
                data.forEach { item->
                    cartItems.add(UserCart.newInstance(item.data!!))
                }
                userCartItems.clear()
                userCartItems.addAll(cartItems)
                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }

    fun addUserCartItem(){
        isLoading.value = true
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        val item = HashMap<String,Any>()
        item["id"] = UUID.randomUUID().toString()
        item["createDate"] = "2023/04/29 20:58"
        item["isChecked"] = false
        item["materialId"] = ""
        item["materialName"] = "Test android"
        item["recipeId"] = ""
        item["userId"] = currentUserId.toString()
        firestore.collection("UserCart")
            .add(item)
            .addOnSuccessListener {
                getUserCartItems()
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }


}