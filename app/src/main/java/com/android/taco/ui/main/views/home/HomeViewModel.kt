package com.android.taco.ui.main.views.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.taco.repository.ApiRepository
import com.android.taco.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ApiRepository
):ViewModel() {
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")
    var infoMessage = mutableStateOf("")

    init {
    }

    private fun makeCall(gameId: String){
        isLoading.value = true
        viewModelScope.launch {
            val result = repository.makeRequest(gameId)
            when(result) {
                is Resource.Success -> {


                }
                is Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }

                else -> {}
            }
        }
    }


}