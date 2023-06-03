package com.android.taco.ui.account

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.taco.R
import com.android.taco.ui.theme.components.buttons.PrimaryButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()
            AccountNavigationHost(navController)
        }
    }

    @Composable
    fun MainAccountScreen(navController: NavHostController){
       // TacoTheme() {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        // Replace with your image id
                        painterResource(id = R.drawable.background_image),
                        contentScale = ContentScale.FillBounds
                    )
            ) {
                //Resim, image özelliğine atanıyor
                Image(painter = painterResource(id = R.drawable.main_account_image), contentDescription = null)

                Text(text = "Lezzete Giden Yolda Sadece Bir Adım",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp))

                PrimaryButton(text = "Giriş Yap") {
                    navController.navigate("LoginScreen")
                }
                Text("Yeni Hesap Oluştur", textAlign = TextAlign.Center, color = Color.White, modifier = Modifier.padding(16.dp))
            }
        //}
    }


    @Composable
    fun AccountNavigationHost(navController: NavHostController) {
        val viewModel: AuthViewModel = viewModel()
        NavHost(
            navController = navController,
            startDestination = "MainAccountScreen"
        ) {
            composable("MainAccountScreen") {
                MainAccountScreen(navController = navController)
            }
            composable("LoginScreen") {
                LoginScreen(navController = navController, viewModel = viewModel)
            }
        }
    }
}