package com.tacomeal.taco.ui.main.views.favourites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tacomeal.taco.model.Recipe
import com.tacomeal.taco.ui.main.ScreensNavItem
import com.tacomeal.taco.ui.main.views.chef.recipe.RecipeCard
import com.tacomeal.taco.ui.theme.BrandPrimary
import com.tacomeal.taco.ui.theme.BrandSecondary

@Composable
fun FavouritesWidget(navController: NavController,
                     viewModel: FavouritesViewModel
){

    if(viewModel.isLoading.value){
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            CircularProgressIndicator(color = BrandSecondary)
        }
    }else if(viewModel.favouritesRecipes.isNotEmpty()) {
        Column(modifier = Modifier
            .fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Favorilerim",
                    color = BrandPrimary,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold),
                    modifier = Modifier)

                Text(
                    text = "Hepsini Gör",
                    color = BrandSecondary,
                    textAlign = TextAlign.End,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold),
                    modifier = Modifier.clickable {
                        navController.navigate(ScreensNavItem.Favourites.screen_route)
                    })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .fillMaxWidth()
            ){
                viewModel.favouritesRecipes.take(3).forEach { recipe ->
                    RecipeCard(recipe = recipe){
                        navController.navigate(ScreensNavItem.Recipe.screen_route+ "/${recipe.id}")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }

        }
    }

}

@Preview
@Composable
fun FavouritesWidgetPreview(){
    FavouritesWidget(navController = rememberNavController(), viewModel = viewModel())
}
