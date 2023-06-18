package com.android.taco.ui.main.views.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.taco.R
import com.android.taco.ui.main.views.chef.plan.DailyMenuWidget
import com.android.taco.ui.main.views.chef.plan.PlanWidget
import com.android.taco.ui.main.views.populars.PopularsWidget
import com.android.taco.ui.theme.*
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(color = Color.White)
    ) {
        Header()

        DailyMenuWidget()
        PlanWidget(navController = navController)
        PopularsWidget(navController = navController)
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
                text = "Günaydın",
                color = Color(0xff0a2533),
                style = TextStyle(
                    fontSize = 14.sp))
        }

        Text(
            text = FirebaseAuth.getInstance().currentUser?.displayName ?: "",
            color = Color(0xff0a2533),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen(navController = rememberNavController())
}

