package com.android.taco.ui.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taco.repository.AuthRepository
import com.android.taco.util.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth
): ViewModel() {

    fun signIn(email : String, password : String, onResult : (res: Resource<Any>)->Unit){
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        onResult.invoke(Resource.Success(true))
                    } else {

                        // If sign in fails, display a message to the user.
                        //updateUI(null)
                    }
                }
        } catch (e: Exception) {
            onResult.invoke(Resource.Error("İşlem sırasında bir hata oluştu"))
        }
    }

}