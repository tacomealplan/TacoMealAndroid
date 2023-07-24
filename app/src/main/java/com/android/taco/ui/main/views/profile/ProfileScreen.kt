package com.android.taco.ui.main.views.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.taco.ui.main.BottomNavItem
import com.android.taco.ui.main.ScreensNavItem
import com.android.taco.ui.main.views.chef.plan.PlanWidget
import com.android.taco.ui.main.views.favourites.FavouritesWidget
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.buttons.CardButton
import com.android.taco.ui.theme.components.cards.ProfileCardView

@Composable
fun ProfileScreen(navController: NavController,
                  viewModel: ProfileViewModel
) {
    TacoTheme() {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .background(Color.White)
        ) {
            ProfileCardView(username = viewModel.username.value,
                bio = viewModel.bio.value,
                ppUrl = viewModel.ppUrl.value
            ) {
                navController.navigate(BottomNavItem.Profile.screen_route +"/Edit")
            }

            Spacer(modifier = Modifier.size(24.dp))

            FavouritesWidget(navController, viewModel = viewModel())

            //Spacer(modifier = Modifier.size(24.dp))

            //PlanWidget(navController = navController)

            Spacer(modifier = Modifier.size(24.dp))

            CardButton(text = "Haftalık Plan Oluştur") {
                navController.navigate(ScreensNavItem.EditPlan.screen_route)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
    ProfileScreen(navController = rememberNavController(), viewModel = viewModel())
}