package com.android.taco.ui.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.bars.PrimaryTopBar
import com.android.taco.ui.theme.components.buttons.PrimaryButton
import com.android.taco.ui.theme.components.buttons.SecondaryButton
import com.android.taco.ui.theme.components.editTexts.EmailTextField
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ForgotPasswordScreen(navController: NavController,
                         viewModel : AuthViewModel
){
    var email by remember { mutableStateOf("acetinkaya892@gmail.com") }
    TacoTheme() {
        Scaffold(topBar = {
            PrimaryTopBar(title = "Parolamı Unuttum") {
                navController.popBackStack()
            }
        }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()) {
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "E-Posta adresinizi girerek parola sıfırlama maili alabilirsiniz",
                    color = BrandPrimary,
                    style = TextStyle(
                        fontSize = 16.sp),
                    modifier = Modifier.padding(horizontal = 24.dp))

                Spacer(modifier = Modifier.size(16.dp))
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    EmailTextField(value = email, label = "E-Posta", placeholder = "E-Posta adresinizi girin"){ value ->
                        email = value
                    }
                }

                Spacer(modifier = Modifier.fillMaxHeight(0.8f))


                PrimaryButton(text = "E-Posta Gönder") {
                    
                }
            }
        }
    }
}

@Preview
@Composable
fun ForgotPasswordScreenPreview(){
    ForgotPasswordScreen(navController = rememberNavController(), viewModel = AuthViewModel(
        FirebaseAuth.getInstance())
    )
}