package com.android.taco.ui.main.views.populars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.taco.ui.main.ScreensNavItem
import com.android.taco.ui.main.views.chef.recipe.RecipeCard
import com.android.taco.ui.theme.BrandSecondary
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.bars.PrimaryTopBar

@Composable
fun PopularsScreen(navController: NavController,
                   viewModel : PopularsViewModel
){
    TacoTheme() {
        Scaffold(topBar = {
            PrimaryTopBar(title = "Popüler Tarifler") {
                navController.popBackStack()
            }
        }){
            if(viewModel.isLoading.value){
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(color = BrandSecondary)
                }
            }else{
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(it)

                ) {
                    items(viewModel.popularRecipes) {recipe ->
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
}

@Preview
@Composable
fun PopularsScreenPreview(){
    PopularsScreen(navController = rememberNavController(), viewModel = PopularsViewModel())
}