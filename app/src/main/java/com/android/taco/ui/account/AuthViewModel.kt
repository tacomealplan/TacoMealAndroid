package com.android.taco.ui.account

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
    private val authRepository: AuthRepository,
    private val auth: FirebaseAuth
): ViewModel() {

    fun logIn() {
        viewModelScope.launch {
            authRepository.logIn("acetinkaya892@gmail.com","123456").collect{

            }

        }
    }

    private fun signIn(){
        auth.signInWithEmailAndPassword("acetinkaya892@gmail.com", "123456")
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {

                    //updateUI(user)
                } else {

                    // If sign in fails, display a message to the user.
                    //updateUI(null)
                }
            }
    }

}