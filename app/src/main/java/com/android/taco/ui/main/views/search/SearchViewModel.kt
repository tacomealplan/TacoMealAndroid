package com.android.taco.ui.main.views.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.android.taco.model.Recipe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
): ViewModel() {
    var isLoading = mutableStateOf(false)
    var favouritesRecipes = mutableListOf<Recipe>()
    init {

    }

    fun searchRecipeByText(searchText : String){

    }




}