package com.android.taco.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taco.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    fun logIn() {
        viewModelScope.launch {
            authRepository.logIn("acetinkaya892@gmail.com","123456").collect{

            }

        }
    }

}