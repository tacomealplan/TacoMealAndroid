package com.android.taco.ui.main.views.chef.plan

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.taco.model.Plan
import com.android.taco.ui.main.ScreensNavItem
import com.android.taco.ui.main.views.chef.recipe.RecipeCardById
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.bars.PrimaryTopBar

@Composable
fun PlanScreen(planId: String,
               viewModel: PlanViewModel,
               navController: NavController
){
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getPlanById(planId)
    })
    var selectedDay by remember {
        mutableStateOf<Day>(Day.Monday)
    }

    val plan by remember {
        viewModel.plan
    }
    TacoTheme() {
        Scaffold(topBar = {
            PrimaryTopBar(title = "Plan Detayı") {
                navController.popBackStack()
            }
        }) {
            Column(verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(it)
                    .verticalScroll(state = rememberScrollState())
                    .padding(12.dp)) {

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .fillMaxWidth()
                ){
                    DayList(selectedDay = selectedDay){day->
                        selectedDay = day
                    }
                }

                Text(
                    text = "Kahvaltı",
                    color = BrandPrimary,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 12.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                ) {
                    plan?.getDay(selectedDay)?.breakfast?.forEach { recipeId ->
                        if(recipeId.isNotBlank())
                            RecipeCardById(recipeId = recipeId, viewModel = viewModel()){
                                navController.navigate(ScreensNavItem.Recipe.screen_route + "/${recipeId}")
                            }
                    }

                }

                Text(
                    text = "Öğle",
                    color = BrandPrimary,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 12.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                ) {
                    plan?.getDay(selectedDay)?.lunch?.forEach { recipeId ->
                        if(recipeId.isNotBlank())
                            RecipeCardById(recipeId = recipeId, viewModel = viewModel()){
                                navController.navigate(ScreensNavItem.Recipe.screen_route + "/${recipeId}")
                            }
                    }

                }

                Text(
                    text = "Akşam",
                    color = BrandPrimary,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 12.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                ) {
                    plan?.getDay(selectedDay)?.dinner?.forEach { recipeId ->
                        if(recipeId.isNotBlank())
                            RecipeCardById(recipeId = recipeId, viewModel = viewModel()){
                                navController.navigate(ScreensNavItem.Recipe.screen_route + "/${recipeId}")
                            }
                    }

                }

            }
        }
    }
}
