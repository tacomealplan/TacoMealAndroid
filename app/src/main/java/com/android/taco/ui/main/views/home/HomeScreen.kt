package com.android.taco.ui.main.views.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.taco.R
import com.android.taco.ui.main.ScreensNavItem
import com.android.taco.ui.main.views.chef.plan.DailyMenuWidget
import com.android.taco.ui.main.views.chef.plan.PlanWidget
import com.android.taco.ui.main.views.populars.PopularsWidget
import com.android.taco.ui.theme.*
import com.android.taco.ui.theme.components.buttons.CardButton
import com.android.taco.util.getTimeMessage
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController) {
    val activePlan by remember {
        viewModel.activePlan
    }
    val hasNoPlan by remember {
        viewModel.hasNoPlan
    }
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getActivePlan()
        viewModel.getMyPlans()
    })

    TacoTheme() {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(24.dp)
                .background(color = Color.White)
        ) {
            Header()
            Spacer(modifier = Modifier.height(12.dp))
            if(activePlan != null){
                DailyMenuWidget(activePlan!!.planId, viewModel = androidx.lifecycle.viewmodel.compose.viewModel()){recipeId ->
                    navController.navigate(ScreensNavItem.Recipe.screen_route+ "/${recipeId}")
                }
                Spacer(modifier = Modifier.height(12.dp))
                PlanWidget(navController = navController, activePlanId = activePlan!!.planId)
                Spacer(modifier = Modifier.height(12.dp))
            }

            PopularsWidget(navController = navController)
            Spacer(modifier = Modifier.height(12.dp))
            AnimatedVisibility(visible = hasNoPlan) {
                CardButton(text = "Haftalık Plan Oluştur") {
                    navController.navigate(ScreensNavItem.EditPlan.screen_route)
                }
            }
        }
    }

}

@Composable
fun Header() {
    Column(horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.sun),
                contentDescription = "Icon/Sun",
                colorFilter = ColorFilter.tint(Color(0xff4d8194)),
                modifier = Modifier
                    .size(size = 20.dp))
            Text(
                text = "${getTimeMessage()} ${FirebaseAuth.getInstance().currentUser?.displayName ?: ""}",
                color = BrandPrimary,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp))
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen(navController = rememberNavController())
}

