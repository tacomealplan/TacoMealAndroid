package com.android.taco.ui.main.views.populars

import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.repository.ApiRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PopularsViewModel @Inject constructor(
): ViewModel() {
    var isLoading = mutableStateOf(false)
    init {
        getPopularRecipes()
    }

    private fun getPopularRecipes(forWidget : Boolean = false){
        isLoading.value = true
        val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        firestore.collection("PopularRecipes")
            .get()
            .addOnSuccessListener {
                var test = it.documents.get(0).data
                print(it.documents.get(0))
                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }


}