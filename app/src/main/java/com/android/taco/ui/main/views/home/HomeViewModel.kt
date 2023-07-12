package com.android.taco.ui.main.views.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taco.model.Recipe
import com.android.taco.model.RecipeDetail
import com.android.taco.model.UserPlan
import com.android.taco.repository.ApiRepository
import com.android.taco.ui.main.views.chef.recipe.getUrlForStorage
import com.android.taco.util.Resource
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
    var recipe = mutableStateOf<Recipe?>(null)
    var activePlan = mutableStateOf<UserPlan?>(null)


    fun getActivePlan(){
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            firestore.collection("UserPlan")
                .whereEqualTo("UserId",it)
                .whereEqualTo("isActive", true)
                .whereEqualTo("WeekOfYear", 1)
                .get()
                .addOnSuccessListener {
                    val data = it.documents
                    data.forEach { item->
                        if(item.data != null)
                            this.activePlan.value = UserPlan.newInstance(item.data!!)
                    }

                }
                .addOnFailureListener{
                    it.printStackTrace()
                }
        }

    }
}