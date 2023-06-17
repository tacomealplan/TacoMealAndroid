package com.android.taco.ui.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.bars.PrimaryTopBar
import com.android.taco.ui.theme.components.buttons.PrimaryButton
import com.android.taco.ui.theme.components.editTexts.EmailTextField
import com.android.taco.ui.theme.components.editTexts.PasswordTextField
import com.android.taco.ui.theme.components.editTexts.PrimaryTextField

@Composable
fun SignUpScreen(navController: NavHostController,
                 viewModel : AuthViewModel
){
    var email by remember { mutableStateOf("acetinkaya892@gmail.com") }
    var password by remember { mutableStateOf("123456") }
    TacoTheme() {
        Scaffold(topBar = {
            PrimaryTopBar(title = "Hesap Oluştur") {
                navController.popBackStack()
            }
        }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(it)
                    .padding(24.dp)
                    .fillMaxSize()) {
                Row {
                    PrimaryTextField(value = "", label = "Ad", placeholder = "Ad", modifier = Modifier.fillMaxWidth(0.5f)){

                    }
                    PrimaryTextField(value = "", label = "Soyad", placeholder = "Soyad"){

                    }
                }

                EmailTextField(value = email, label = "E-Posta", placeholder = "E-Posta adresinizi girin"){ value ->
                    email = value
                }
                PasswordTextField(value = password, label = "Parola", placeholder = "Parolanızı girin"){value ->
                    password = value
                }


                Spacer(modifier = Modifier.fillMaxHeight(0.15f))
                PrimaryButton(text = "Devam Et") {

                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    textAlign = TextAlign.Center,
                    text = "Devam ederek şunları kabul etmiş olursunuz ",
                    color = Color(0xff48525f),
                    fontSize = 14.sp)
                Row() {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Kullanıcı Sözleşmesi",
                        color = BrandPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable {

                        }
                    )

                    Text(
                        textAlign = TextAlign.Center,
                        text = " & ",
                        color = Color(0xff48525f),
                        fontSize = 14.sp)

                    Text(
                        textAlign = TextAlign.Center,
                        text = "Gizlilik Politikasını",
                        fontWeight = FontWeight.Bold,
                        color = BrandPrimary,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable {

                        }
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview(){
    SignUpScreen(navController = rememberNavController(), viewModel = viewModel() )
}