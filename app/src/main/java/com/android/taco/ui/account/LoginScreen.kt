package com.android.taco.ui.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.android.taco.ui.theme.components.bars.PrimaryTopBar
import com.android.taco.ui.theme.components.buttons.GoogleButton
import com.android.taco.ui.theme.components.buttons.PrimaryButton
import com.android.taco.ui.theme.components.editTexts.EmailTextField
import com.android.taco.ui.theme.components.editTexts.PasswordTextField
import com.android.taco.ui.theme.components.editTexts.PrimaryTextField
import com.android.taco.util.Resource

@Composable
fun LoginScreen(navController: NavHostController,
                viewModel : AuthViewModel
){
    val context = LocalContext.current

    TacoTheme() {
        Scaffold(
            topBar = {
                PrimaryTopBar(title = "Giriş Yap"){
                    navController.popBackStack()
                }
            }
        ) { it ->
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(it)

            ) {
                var email by remember { mutableStateOf("acetinkaya892@gmail.com") }
                var password by remember { mutableStateOf("123456") }
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .padding(horizontal = 24.dp)
                ) {
                    EmailTextField(value = email, label = "E-Posta", placeholder = "E-Posta adresinizi girin"){ value ->
                        email = value
                    }
                    PasswordTextField(value = password, label = "Parola", placeholder = "Parolanızı girin"){value ->
                        password = value
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    PrimaryButton(text = "Giriş Yap") {
                        viewModel.signIn(email = email, password = password) {
                            when (it) {
                                is Resource.Success -> {
                                    MainActivity.start(context)
                                }

                                is Resource.Error -> {

                                }
                            }
                        }

                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = "Parolanı mı unuttun?",
                        color = Color(0xff0a2533),
                        textAlign = TextAlign.Center,
                        lineHeight = 135.sp,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold),
                        modifier = Modifier.clickable {
                            navController.navigate("ForgotPasswordScreen")
                        }
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