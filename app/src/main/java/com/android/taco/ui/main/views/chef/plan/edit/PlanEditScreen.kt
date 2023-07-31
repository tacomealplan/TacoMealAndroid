package com.android.taco.ui.main.views.chef.plan.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.taco.model.Plan
import com.android.taco.ui.main.ScreensNavItem
import com.android.taco.ui.main.views.chef.plan.Day
import com.android.taco.ui.main.views.chef.plan.DayList
import com.android.taco.ui.main.views.chef.plan.PlanViewModel
import com.android.taco.ui.main.views.chef.recipe.RecipeCardById
import com.android.taco.ui.main.views.chef.recipe.edit.EditRecipeDetailScreen
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.BrandSecondary
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.bars.PrimaryTopBar
import com.android.taco.ui.theme.components.buttons.PrimaryButton
import com.android.taco.ui.theme.components.buttons.SecondaryButton

@Composable
fun PlanEditScreen(planId: String,
               viewModel: PlanEditViewModel,
               navController: NavController
){
    var planDetailDialog by remember{mutableStateOf(false) }
    LaunchedEffect(key1 = Unit, block = {
        if(planId.isNotEmpty()){
            viewModel.getPlanById(planId)
        }else{
            viewModel.createNewInstance()
        }

    })
    var selectedDay by remember {
        mutableStateOf<Day>(Day.Monday)
    }

    val plan by remember {
        viewModel.plan
    }
    TacoTheme() {
        Scaffold(topBar = {
            PrimaryTopBar(title = "Haftalık Plan Oluştur") {
                navController.popBackStack()
            }
        }, bottomBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
            ) {
                SecondaryButton(text = "İleri") {
                    planDetailDialog = true
                }
            }
        }) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(it)
                        .verticalScroll(state = rememberScrollState())
                        .fillMaxHeight(0.9f)
                        .fillMaxWidth()) {

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

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Text(
                            text = "Kahvaltı",
                            color = BrandPrimary,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(start = 12.dp)
                        )

                        Text(
                            text = "Tarif Ekle",
                            color = BrandSecondary,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .clickable {

                                }
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.horizontalScroll(state = rememberScrollState())
                    ) {
                        plan?.getDay(selectedDay)?.breakfast?.forEach { recipeId ->
                            if(recipeId.isNotBlank())
                                RecipeCardById(recipeId = recipeId, viewModel = viewModel()){
                                    navController.navigate(ScreensNavItem.Recipe.screen_route + "/${recipeId}")
                                }
                        }

                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Text(
                            text = "Öğle",
                            color = BrandPrimary,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(start = 12.dp)
                        )

                        Text(
                            text = "Tarif Ekle",
                            color = BrandSecondary,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .clickable {

                                }
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.horizontalScroll(state = rememberScrollState())
                    ) {
                        plan?.getDay(selectedDay)?.lunch?.forEach { recipeId ->
                            if(recipeId.isNotBlank())
                                RecipeCardById(recipeId = recipeId, viewModel = viewModel()){
                                    navController.navigate(ScreensNavItem.Recipe.screen_route + "/${recipeId}")
                                }
                        }

                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Text(
                            text = "Akşam",
                            color = BrandPrimary,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(start = 12.dp)
                        )

                        Text(
                            text = "Tarif Ekle",
                            color = BrandSecondary,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .clickable {

                                }
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.horizontalScroll(state = rememberScrollState())
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
            if(planDetailDialog){
                TacoTheme() {
                    Dialog(properties = DialogProperties(usePlatformDefaultWidth = false),
                        onDismissRequest = { planDetailDialog = false}) {
                        PlanInfoEditScreen(navController = navController, viewModel = viewModel)
                    }
                }
            }
        }
    }
}
