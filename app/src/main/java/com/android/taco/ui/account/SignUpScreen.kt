package com.android.taco.ui.account

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.taco.ui.main.MainActivity
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.bars.PrimaryTopBar
import com.android.taco.ui.theme.components.buttons.PrimaryButton
import com.android.taco.ui.theme.components.editTexts.EmailTextField
import com.android.taco.ui.theme.components.editTexts.PasswordTextField
import com.android.taco.ui.theme.components.editTexts.PrimaryTextField
import com.android.taco.ui.theme.components.loadingBar.CircularProgress
import com.android.taco.util.Resource


@Composable
fun SignUpScreen(navController: NavHostController,
                 viewModel : AuthViewModel
){
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }

    TacoTheme() {
        Scaffold(topBar = {
            PrimaryTopBar(title = "Hesap Oluştur") {
                navController.popBackStack()
            }
        }) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(it)
                        .padding(24.dp)
                        .fillMaxSize()) {

                    PrimaryTextField(value = userName, label = "Kullanıcı Adı", placeholder = "Kullanıcı Adı"){value->
                        userName = value
                    }
                    EmailTextField(value = email, label = "E-Posta", placeholder = "E-Posta adresinizi girin"){ value ->
                        email = value
                    }
                    PasswordTextField(value = password, label = "Parola", placeholder = "Parolanızı girin"){value ->
                        password = value
                    }


                    Spacer(modifier = Modifier.fillMaxHeight(0.15f))
                    PrimaryButton(text = "Devam Et") {
                        viewModel.signUp(username = userName, email = email, password = password){
                            when (it) {
                                is Resource.Success -> {
                                    MainActivity.start(context)
                                }
                                is Resource.Error -> {

                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Devam ederek şunları kabul etmiş olursunuz ",
                        color = BrandPrimary,
                        fontSize = 16.sp)
                    Row() {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "Kullanıcı Sözleşmesi",
                            color = BrandPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.clickable {
                                //"https://www.tacomealplan.com/sozlesmeler"
                                val browserIntent =
                                    Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tacomealplan.com/sozlesmeler"))
                                context.startActivity(browserIntent)
                            }
                        )

                        Text(
                            textAlign = TextAlign.Center,
                            text = " & ",
                            color = Color(0xff48525f),
                            fontSize = 16.sp)

                        Text(
                            textAlign = TextAlign.Center,
                            text = "Gizlilik Politikasını",
                            fontWeight = FontWeight.Bold,
                            color = BrandPrimary,
                            fontSize = 16.sp,
                            modifier = Modifier.clickable {
                                //https://www.tacomealplan.com/kvkk
                                val browserIntent =
                                    Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tacomealplan.com/kvkk"))
                                context.startActivity(browserIntent)
                            }
                        )
                    }

                }
                if(viewModel.isLoading.value){
                    CircularProgress()
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