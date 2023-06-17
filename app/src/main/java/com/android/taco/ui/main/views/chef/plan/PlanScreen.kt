package com.android.taco.ui.main.views.chef.plan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.bars.PrimaryTopBar

@Composable
fun PlanScreen(navController: NavController){
    TacoTheme() {
        Scaffold(topBar = {
            PrimaryTopBar(title = "Haftalık Planım") {
                navController.popBackStack()
            }
        }) {
            Column(verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(it)) {

            }
        }
    }
}

@Preview
@Composable
fun PlanScreenPreview(){
    PlanScreen(navController = rememberNavController())
}