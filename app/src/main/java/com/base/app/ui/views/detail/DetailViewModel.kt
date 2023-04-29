package com.base.app.ui.views.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.base.app.repository.ApiRepository
import com.base.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    var repository: ApiRepository
) :ViewModel() {
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")


    fun changeLoadingState(){
        isLoading.value = !isLoading.value
    }


}