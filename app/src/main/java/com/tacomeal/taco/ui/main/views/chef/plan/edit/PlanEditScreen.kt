package com.tacomeal.taco.ui.main.views.chef.plan.edit

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.tacomeal.taco.model.Day
import com.tacomeal.taco.model.Meal
import com.tacomeal.taco.model.Plan
import com.tacomeal.taco.model.Recipe
import com.tacomeal.taco.ui.main.ScreensNavItem
import com.tacomeal.taco.ui.main.views.chef.plan.DayList
import com.tacomeal.taco.ui.main.views.chef.recipe.RecipeCard
import com.tacomeal.taco.ui.theme.BrandPrimary
import com.tacomeal.taco.ui.theme.BrandSecondary
import com.tacomeal.taco.ui.theme.TacoTheme
import com.tacomeal.taco.ui.theme.components.bars.PrimaryTopBar
import com.tacomeal.taco.ui.theme.components.buttons.SecondaryButton
import com.tacomeal.taco.ui.theme.components.dialogBox.ErrorDialog
import com.tacomeal.taco.ui.theme.components.dialogBox.SuccessDialog
import com.tacomeal.taco.ui.theme.components.dialogBox.WarningDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
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

    val coroutineScope = rememberCoroutineScope()
    val addRecipeSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmValueChange = { it != ModalBottomSheetValue.Expanded }
    )
    var selectedDay by remember { mutableStateOf<Day>(Day.Monday) }
    var selectedMeal by remember { mutableStateOf<Meal>(Meal.Breakfast) }
    val plan by remember { viewModel.plan }
    val planRecipes = remember { viewModel.planRecipes }
    TacoTheme() {
        ModalBottomSheetLayout(
            sheetState = addRecipeSheetState,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetElevation = 9.dp,
            sheetContent = {
                AddRecipeToPlanScreen(viewModel = androidx.lifecycle.viewmodel.compose.viewModel()){ recipe ->
                    viewModel.addRecipeToPlan(recipe = recipe, day = selectedDay, meal = selectedMeal)
                    coroutineScope.launch {
                        addRecipeSheetState.hide()
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(topBar = {
                PrimaryTopBar(title = if(planId.isNotEmpty()) "Haftalık Plan Oluştur" else "Haftalık Plan Düzenle") {
                    navController.popBackStack()
                }
            }, bottomBar = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
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

                        Meal.values().forEach { meal ->
                            MealRecipes(
                                meal = meal,
                                planRecipes = planRecipes,
                                plan = plan,
                                selectedDay = selectedDay,
                                navController = navController
                            ){
                                selectedMeal = meal
                                coroutineScope.launch {
                                    addRecipeSheetState.show()
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
}

@Composable
private fun MealRecipes(
    meal : Meal,
    planRecipes: SnapshotStateList<Recipe>,
    plan: Plan?,
    selectedDay: Day,
    navController: NavController,
    addRecipe : () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Text(
            text = meal.name,
            color = BrandPrimary,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(start = 12.dp)
        )

        Text(
            text = "Tarif Ekle",
            color = BrandSecondary,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(start = 12.dp)
                .clickable {
                    addRecipe.invoke()
                }
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.horizontalScroll(state = rememberScrollState())
    ) {
        if (planRecipes.isNotEmpty()) {
            plan?.getDay(selectedDay)?.getRecipesByMeal(meal)?.forEach { recipeId ->
                if (recipeId.isNotBlank() && planRecipes.any { it.id == recipeId })
                    RecipeCard(recipe = planRecipes.first { it.id == recipeId }) {
                        navController.navigate(ScreensNavItem.Recipe.screen_route + "/${recipeId}")
                    }
            }
        }
    }
}
