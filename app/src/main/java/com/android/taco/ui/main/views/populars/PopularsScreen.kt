package com.android.taco.ui.main.views.populars

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.bars.PrimaryTopBar

@Composable
fun PopularsScreen(navController: NavController,
                   viewModel : PopularsViewModel
){
    TacoTheme() {
        Scaffold(topBar = {
            PrimaryTopBar(title = "Popüler Tarifler") {
                navController.popBackStack()
            }
        }){

        }
    }
}

@Preview
@Composable
fun PopularsScreenPreview(){
    PopularsScreen(navController = rememberNavController(), viewModel = PopularsViewModel())
}