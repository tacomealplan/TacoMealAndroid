package com.android.taco.ui.main.views.chef

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.taco.R
import com.android.taco.model.Plan
import com.android.taco.ui.main.ScreensNavItem
import com.android.taco.ui.main.views.chef.planList.PlanListScreen
import com.android.taco.ui.main.views.chef.recipeList.RecipeListScreen
import com.android.taco.ui.main.views.favourites.FavouritesViewModel
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.BrandSecondary
import com.android.taco.ui.theme.NeutralGray4
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.tabs.TabsTwoOptions
import com.google.gson.Gson

@Composable
fun ChefScreen(navController: NavController,
               viewModel: ChefViewModel
) {
    var selectedTab by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getMyPlans()
        viewModel.getMyRecipes()
    })
    TacoTheme() {
        Scaffold(topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                TabsTwoOptions(tabs = arrayListOf("Tariflerim", "Planlarım"),selectedTab = selectedTab, onTabSelected = { selected ->
                    selectedTab = selected
                })
            }

        }) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(Color.White)
            ) {
                if(selectedTab == 0){
                    RecipeListScreen(navController = navController, recipeList = viewModel.myRecipes.toList()){selectedRecipe ->
                        navController.navigate(ScreensNavItem.Recipe.screen_route + "/${selectedRecipe.id}")
                    }
                }else{
                    PlanListScreen(navController = navController, planList = viewModel.myPlans.toList() as ArrayList<Plan>){ selectedPlan ->
                        navController.navigate(ScreensNavItem.Plan.screen_route + "/${selectedPlan.id}")
                    }
                }
            }
        }

    }
}