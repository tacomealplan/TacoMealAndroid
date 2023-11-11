package com.tacomeal.taco.ui.main.views.chef.plan.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.tacomeal.taco.model.Day
import com.tacomeal.taco.model.Meal
import com.tacomeal.taco.model.Recipe
import com.tacomeal.taco.ui.main.views.chef.ChefViewModel
import com.tacomeal.taco.ui.main.views.chef.recipeList.RecipeListScreen
import com.tacomeal.taco.ui.main.views.search.SearchScreen
import com.tacomeal.taco.ui.theme.TacoTheme
import com.tacomeal.taco.ui.theme.components.tabs.TabsTwoOptions

@Composable
fun AddRecipeToPlanScreen(viewModel: ChefViewModel,
                          onRecipeSelected : (recipe : Recipe) -> Unit
){
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getMyRecipes()
    })
    var selectedTab by remember {
        mutableStateOf(0)
    }
    var myRecipelist = remember {
        viewModel.myRecipes
    }
    TacoTheme {
        Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
            TabsTwoOptions(tabs = arrayListOf("Tariflerim", "Ara"), selectedTab =selectedTab , onTabSelected = {
                selectedTab = it
            })
            if(selectedTab == 0){
                RecipeListScreen(navController = rememberNavController(), recipeList = myRecipelist, onRecipeSelected = {recipe ->
                    onRecipeSelected.invoke(recipe)
                })
            }else{
                SearchScreen(navController = rememberNavController(), viewModel = viewModel(), forSelectingRecipes = true, onRecipeSelected = {recipe ->
                    onRecipeSelected.invoke(recipe)
                })
            }

        }
    }
}