package com.android.taco.ui.main.views.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.model.Recipe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
): ViewModel() {
    var isLoading = mutableStateOf(false)
    var username = mutableStateOf<String>("")
    var ppUrl = mutableStateOf<String>("")
    var bio = mutableStateOf<String>("")
    init {
        username.value = FirebaseAuth.getInstance().currentUser?.displayName ?: ""
        ppUrl.value = FirebaseAuth.getInstance().currentUser?.photoUrl.toString()
    }

    private fun getUserBio(){

    }

    fun updateProfile(){

    }

    fun sigOut(){
        FirebaseAuth.getInstance().signOut()
    }



}