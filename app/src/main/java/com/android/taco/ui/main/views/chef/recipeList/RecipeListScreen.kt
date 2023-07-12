package com.android.taco.ui.main.views.chef.recipeList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.taco.model.Plan
import com.android.taco.model.Recipe
import com.android.taco.ui.main.ScreensNavItem
import com.android.taco.ui.main.views.chef.recipe.RecipeCard
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.buttons.SecondaryButton

@Composable
fun RecipeListScreen(navController: NavController,
                     recipeList: List<Recipe>,
                     onRecipeSelected : (recipe : Recipe) -> Unit){
    TacoTheme() {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .background(Color.White)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxHeight(0.9f)

            ) {
                items(recipeList) {recipe ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        RecipeCard(recipe = recipe){
                            onRecipeSelected.invoke(recipe)
                        }
                    }
                }
            }

            SecondaryButton(text = "Tarif Ekle") {
                navController.navigate(ScreensNavItem.EditRecipe.screen_route)
            }
        }

    }
}