package com.tacomeal.taco.ui.account

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tacomeal.taco.util.Resource
import com.tacomeal.taco.util.isEmailValid
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth
): ViewModel() {
    var isLoading = mutableStateOf(false)
    var sendPasswordResetEmailIsValid = mutableStateOf(true)

    fun signIn(email : String, password : String, onResult : (res: Resource<Any>)->Unit){
        isLoading.value = true
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        isLoading.value = false
                        onResult.invoke(Resource.Success(true))
                    } else {
                        isLoading.value = false
                        onResult.invoke(Resource.Error("İşlem sırasında bir hata oluştu"))
                    }
                }
        } catch (e: Exception) {
            isLoading.value = false
            onResult.invoke(Resource.Error("İşlem sırasında bir hata oluştu"))
        }
    }

    fun signUp(username : String, email : String, password : String, onResult : (res: Resource<Any>)->Unit){
        isLoading.value = true
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        val user = Firebase.auth.currentUser

                        val profileUpdates = userProfileChangeRequest {
                            displayName = username
                            photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
                        }

                        user!!.updateProfile(profileUpdates)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    isLoading.value = false
                                    onResult.invoke(Resource.Success(true))
                                }
                            }
                    } else {
                        // If sign in fails, display a message to the user.
                        //updateUI(null)
                    }
                }
        } catch (e: Exception) {
            isLoading.value = false
            onResult.invoke(Resource.Error("İşlem sırasında bir hata oluştu"))
        }
    }

    fun sendPasswordResetEmail(email : String, onResult : (res: Resource<Any>)->Unit){
        if(!email.isEmailValid()){
            sendPasswordResetEmailIsValid.value = false
            return
        }
        isLoading.value = true
        val auth = FirebaseAuth.getInstance()

        auth.sendPasswordResetEmail(email)
        .addOnCompleteListener { task ->
            isLoading.value = false
            if (task.isSuccessful) {
                onResult.invoke(Resource.Success(true))
            }else{
                onResult.invoke(Resource.Error("İşlem sırasında bir hata oluştu, lütfen daha sonra tekrar deneyiniz"))
            }
        }
    }

}