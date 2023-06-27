package com.android.taco.ui.main.views.cart

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.model.Recipe
import com.android.taco.model.UserCart
import com.android.taco.repository.ApiRepository
import com.android.taco.util.getDate
import com.android.taco.util.now
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
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
        isLoading.value = true
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
                    if(!exclude)
                        cartItems.add(cartItem)
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

    fun addUserCartItem(materialName : String){
        isLoading.value = true
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        val item = HashMap<String,Any>()
        item["id"] = UUID.randomUUID().toString()
        item["createDate"] = now()
        item["updateDate"] = now()
        item["isChecked"] = false
        item["materialId"] = ""
        item["materialName"] = materialName
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

    fun checkCartItem(cartItem : UserCart){
        cartItem.createDate = now()
        firestore.collection("UserCart")
            .whereEqualTo("id", cartItem.id)
            .get()
            .addOnSuccessListener {
                val data = it.documents
                data.forEach { item->
                    firestore.collection("UserCart").document(item.id).update("updateDate", now(),
                        "isChecked", cartItem.isChecked)
                }
                isLoading.value = true
                userCartItems.find { it.id == cartItem.id }?.isChecked = cartItem.isChecked
                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()

            }
    }
}