package com.android.taco.ui.main.views.chef.plan.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.android.taco.ui.main.ScreensNavItem
import com.android.taco.ui.main.views.chef.plan.Day
import com.android.taco.ui.main.views.chef.plan.DayList
import com.android.taco.ui.main.views.chef.plan.PlanViewModel
import com.android.taco.ui.main.views.chef.recipe.RecipeCardById
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.BrandSecondary
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.bars.PrimaryTopBar
import com.android.taco.ui.theme.components.buttons.SecondaryButton
import com.android.taco.ui.theme.components.loadingBar.CircularProgress
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlanScreen(planId: String,
               viewModel: PlanViewModel,
               navController: NavController
){
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getPlanById(planId)
    })
    var selectedDay by remember {
        mutableStateOf<Day>(Day.Monday)
    }

    val plan by remember {
        viewModel.plan
    }
    if(viewModel.isLoading.value){
        CircularProgress()
    }
    TacoTheme() {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetElevation = 9.dp,
            sheetContent = {
                Text(text = "Bottom Shhet")
            },
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(topBar = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        PrimaryTopBar(title = "Plan Detayı") {
                            navController.popBackStack()
                        }
                    }
                    if(plan != null && plan!!.createdBy == FirebaseAuth.getInstance().currentUser?.uid ?: ""){
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = "Localized description",
                            tint = BrandSecondary,
                            modifier = Modifier
                                .size(48.dp)
                                .clickable {
                                    navController.navigate(ScreensNavItem.EditPlan.screen_route + "/$planId")
                                })
                    }
                }

            }, bottomBar = {
                //if(viewModel.activePlan.value?.planId != planId){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .background(color = Color.Transparent)
                ) {
                    SecondaryButton(text = "Planı Seç") {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    }
                }
                // }
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
                        modifier = Modifier.horizontalScroll(state = rememberScrollState())
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
                        modifier = Modifier.horizontalScroll(state = rememberScrollState())
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
        }

    }
}
