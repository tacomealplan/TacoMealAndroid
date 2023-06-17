package com.android.taco.ui.main.views.favourites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.bars.PrimaryTopBar

@Composable
fun FavouritesScreen(navController: NavController){
    TacoTheme() {
        Scaffold(topBar = {
            PrimaryTopBar(title = "Favorilerim") {
                navController.popBackStack()
            }
        }){

        }
    }
}

@Preview
@Composable
fun FavouritesScreenPreview(){
    FavouritesScreen(navController = rememberNavController())
}