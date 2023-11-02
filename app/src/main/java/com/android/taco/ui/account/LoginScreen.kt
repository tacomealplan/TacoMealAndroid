package com.android.taco.ui.account

import android.content.Intent
import android.provider.Settings.Global.getString
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.taco.R
import com.android.taco.ui.main.MainActivity
import com.android.taco.ui.theme.NeutralGray2
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.bars.PrimaryTopBar
import com.android.taco.ui.theme.components.buttons.GoogleButton
import com.android.taco.ui.theme.components.buttons.PrimaryButton
import com.android.taco.ui.theme.components.dialogBox.ErrorDialog
import com.android.taco.ui.theme.components.editTexts.EmailTextField
import com.android.taco.ui.theme.components.editTexts.PasswordTextField
import com.android.taco.ui.theme.components.editTexts.PrimaryTextField
import com.android.taco.ui.theme.components.loadingBar.CircularProgress
import com.android.taco.util.Resource
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun LoginScreen(navController: NavHostController,
                viewModel : AuthViewModel
){
    val context = LocalContext.current
    var showErrorDialog by remember { mutableStateOf(false) }
    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            viewModel.isLoading.value = false
            MainActivity.start(context)
        },
        onAuthError = {
            viewModel.isLoading.value = false
        }
    )
    TacoTheme() {
        Scaffold(
            topBar = {
                PrimaryTopBar(title = "Giriş Yap"){
                    navController.popBackStack()
                }
            }
        ) { it ->
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxWidth()
                        .padding(it)

                ) {
                    var email by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }
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
                                        showErrorDialog = true
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
                            viewModel.isLoading.value = true
                            val gso =
                                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(context.getString(R.string.your_web_client_id))
                                    .requestEmail()
                                    .build()
                            val googleSignInClient = GoogleSignIn.getClient(context, gso)
                            launcher.launch(googleSignInClient.signInIntent)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                if(viewModel.isLoading.value){
                    CircularProgress()
                }

                if(showErrorDialog){
                    ErrorDialog(message = "Kullanıcı adı veya şifre hatalı") {
                        showErrorDialog = false
                    }
                }
            }

        }
    }
}

@Composable
fun rememberFirebaseAuthLauncher(
    onAuthComplete: (AuthResult) -> Unit,
    onAuthError: (ApiException) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            scope.launch {
                val authResult = Firebase.auth.signInWithCredential(credential).await()
                onAuthComplete(authResult)
            }
        } catch (e: ApiException) {
            onAuthError(e)
        }
    }
}

/*@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen(navController = rememberNavController())
}*/