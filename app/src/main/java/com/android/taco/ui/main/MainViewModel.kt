package com.android.taco.ui.main

import androidx.lifecycle.ViewModel
import com.android.taco.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ApiRepository
): ViewModel() {

}