package com.android.taco.ui.main.views.cart

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    var repository: ApiRepository
) :ViewModel() {
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")


    fun changeLoadingState(){
        isLoading.value = !isLoading.value
    }


}