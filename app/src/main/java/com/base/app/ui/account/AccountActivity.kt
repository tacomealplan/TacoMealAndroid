package com.base.app.ui.account

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.base.app.R
import com.base.app.ui.main.views.detail.StoryDetailScreen
import com.base.app.ui.main.views.home.HomeScreen

class AccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
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

                Button(onClick = {},
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)

                ) {
                    Text(text = "Giriş Yap", textAlign = TextAlign.Center, color = Color.White)
                }
            }
        }
    }


    @Composable
    fun NavigationHost() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "home_screen"
        ) {
            composable("home_screen") {
                HomeScreen(navController = navController)
            }
            composable(
                "detail_screen/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                    }
                )
            ) {
                val id = remember {
                    it.arguments?.getString("id")
                }
                StoryDetailScreen(
                    id = id ?: "",
                    navController = navController
                )
            }
        }
    }
}