package com.android.taco.ui.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.taco.ui.main.MainActivity
import com.android.taco.ui.theme.NeutralGray2
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.buttons.GoogleButton
import com.android.taco.ui.theme.components.buttons.PrimaryButton
import com.android.taco.ui.theme.components.editTexts.EmailTextField
import com.android.taco.ui.theme.components.editTexts.PasswordTextField
import com.android.taco.ui.theme.components.editTexts.PrimaryTextField

@Composable
fun LoginScreen(navController: NavHostController, viewModel : AuthViewModel){
    val context = LocalContext.current

    //val viewModel : AuthViewModel = hiltViewModel()
    TacoTheme() {
        Scaffold(
            topBar = {
                Row(horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
                ) {
                    Icon(Icons.Rounded.ArrowBack, contentDescription = "Localized description",
                        modifier = Modifier)
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
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(it)

            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .padding(horizontal = 24.dp)
                ) {
                    EmailTextField(label = "E-Posta", placeholder = "E-Posta adresinizi girin")
                    PasswordTextField(label = "Parola", placeholder = "Parolanızı girin")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    PrimaryButton(text = "Giriş Yap") {
                        viewModel.logIn()
                        MainActivity.start(context)
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Parolanı mı unuttun?",
                        color = Color(0xff0a2533),
                        textAlign = TextAlign.Center,
                        lineHeight = 135.sp,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold)
                    )
                }


                
                Spacer(modifier = Modifier.height(140.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "veya diğer hesaplarınızla girin",
                        color = NeutralGray2,
                        textAlign = TextAlign.Center,
                        lineHeight = 145.sp,
                        style = TextStyle(
                            fontSize = 16.sp))
                    Spacer(modifier = Modifier.size(16.dp))
                    GoogleButton {

                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

/*@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen(navController = rememberNavController())
}*/