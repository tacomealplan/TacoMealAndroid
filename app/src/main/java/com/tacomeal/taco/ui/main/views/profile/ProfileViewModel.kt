package com.tacomeal.taco.ui.main.views.profile

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tacomeal.taco.model.Recipe
import com.tacomeal.taco.model.UserCart
import com.tacomeal.taco.util.Resource
import com.tacomeal.taco.util.now
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
): ViewModel() {
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    var isLoading = mutableStateOf(false)
    var username = mutableStateOf("")
    var ppUrl = mutableStateOf("")
    var bio = mutableStateOf("")
    init {
        username.value = FirebaseAuth.getInstance().currentUser?.displayName ?: ""
        var ppUri = FirebaseAuth.getInstance().currentUser?.photoUrl
        ppUrl.value = (ppUri ?: "").toString()
        if(ppUrl.value.isBlank()){
            ppUrl.value = "https://firebasestorage.googleapis.com/v0/b/pisirgecus.appspot.com/o/ProfileImages%2Fdefault.jpeg?alt=media&token=ff0ac150-1e61-4953-a170-00c100eeae1a"
        }
        getUserBio()
    }

    private fun getUserBio(){
        isLoading.value = true
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        firestore.collection("UserBio")
            .whereEqualTo("userId", currentUserId)
            .get()
            .addOnSuccessListener {
                try {
                    val data = it.documents
                    bio.value = data.first().data?.get("bio").toString()
                } catch (e: Exception) {
                    bio.value = ""
                }
                isLoading.value = false
            }
            .addOnFailureListener{
                it.printStackTrace()
                isLoading.value = false
            }
    }

    fun updateProfile(onResult : (result : Boolean) -> Unit){
        isLoading.value = true
        updateBio() { result ->
            if(result){
                updateUsername {res->
                    isLoading.value = false
                    onResult.invoke(res)
                }
            }else{
                onResult.invoke(false)
                isLoading.value = false
            }
        }
    }

    private fun updateUsername(onResult : (result : Boolean) -> Unit){
        val user = Firebase.auth.currentUser
        val profileUpdates = userProfileChangeRequest {
            displayName = username.value
            //photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult.invoke(true)
                }else{
                    onResult.invoke(false)
                }
            }
    }
    private fun updateBio(onResult : (result : Boolean) -> Unit){
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        currentUserId?.let {userId ->
            firestore.collection("UserBio").document(userId).update("userId", userId, "bio", this.bio.value)
            .addOnSuccessListener {
                onResult.invoke(true)
            }.addOnFailureListener {
                val data = hashMapOf("userId" to userId,"bio" to this.bio.value)
                firestore.collection("UserBio").document(userId)
                    .set(data)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onResult.invoke(true)
                        }else{
                            onResult.invoke(false)
                        }
                    }
            }


        }
    }
    fun sigOut(signedOut : () -> Unit){
        isLoading.value = true
        FirebaseAuth.getInstance().signOut()
        signedOut.invoke()
        isLoading.value = false
    }



}