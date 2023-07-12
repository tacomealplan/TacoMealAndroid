package com.android.taco.ui.main.views.chef.recipe.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.White
import com.android.taco.ui.theme.components.bars.TitleTopBar
import com.android.taco.ui.theme.components.tabs.TabsTwoOptions

@Composable
fun EditRecipeDetailScreen(navController: NavController,
                           viewModel: EditRecipeViewModel
){
    var selectedTab by remember{
        mutableStateOf(0)
    }
    TacoTheme() {
        Scaffold(topBar = {
            TitleTopBar(title = "Adımlar & Malzemeler")
        }) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(color = White)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TabsTwoOptions(tabs = arrayListOf("Malzemeler", "Adımlar"), selectedTab = selectedTab, onTabSelected = {tab->
                        selectedTab = tab
                    })
                }

                if(selectedTab == 0){
                    MaterialsEditListScreen(viewModel)
                }else{
                    StepsEditListScreen(viewModel)
                }
            }
        }
    }
}

@Preview
@Composable
fun EditRecipeDetailScreenPreview(){
    EditRecipeDetailScreen(navController = rememberNavController(), viewModel = viewModel())
}
