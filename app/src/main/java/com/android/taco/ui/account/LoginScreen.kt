package com.android.taco.ui.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.taco.ui.main.MainActivity
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.buttons.GoogleButton
import com.android.taco.ui.theme.components.buttons.PrimaryButton
import com.android.taco.ui.theme.components.editTexts.PrimaryTextField

@Composable
fun LoginScreen(navController: NavHostController){
    val context = LocalContext.current
    //val viewModel : AuthViewModel = hiltViewModel()
    TacoTheme() {
        Scaffold(
            topBar = {
                Row(horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Giriş Yap",
                        color = Color(0xff0a2533),
                        textAlign = TextAlign.Center,
                        lineHeight = 135.sp,
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold))
                }

            }
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                PrimaryTextField(label = "E-Posta", placeholder = "E-Posta adresinizi girin")

                PrimaryTextField(label = "Parola", placeholder = "Parolanızı girin")

                PrimaryButton(text = "Giriş Yap") {
                    //viewModel.logIn()
                    MainActivity.start(context)
                }
                Text(
                    text = "Parolanı mı unuttun?",
                    color = Color(0xff0a2533),
                    textAlign = TextAlign.Center,
                    lineHeight = 135.sp,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold))
                
                Spacer(modifier = Modifier.height(140.dp))

                Text(
                    text = "veya diğer hesaplarınızla girin",
                    color = Color(0xff97a2b0),
                    textAlign = TextAlign.Center,
                    lineHeight = 145.sp,
                    style = TextStyle(
                        fontSize = 16.sp))
                GoogleButton {

                }
            }
        }
    }
}