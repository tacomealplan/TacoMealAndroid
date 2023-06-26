package com.android.taco.ui.main.views.favourites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.taco.ui.main.ScreensNavItem
import com.android.taco.ui.main.views.chef.recipe.RecipeCard
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.bars.PrimaryTopBar

@Composable
fun FavouritesScreen(navController: NavController,
                     viewModel: FavouritesViewModel
){
    TacoTheme() {
        Scaffold(topBar = {
            PrimaryTopBar(title = "Favorilerim") {
                navController.popBackStack()
            }
        }){
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(it)

            ) {
                items(viewModel.favouritesRecipes) {recipe ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        RecipeCard(recipe = recipe){
                            navController.navigate(ScreensNavItem.Recipe.screen_route+ "/${recipe.id}")
                        }
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun FavouritesScreenPreview(){
    FavouritesScreen(navController = rememberNavController(), viewModel = viewModel())
}