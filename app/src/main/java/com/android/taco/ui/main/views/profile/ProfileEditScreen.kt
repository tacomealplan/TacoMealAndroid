package com.android.taco.ui.main.views.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.taco.ui.account.AccountActivity
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.bars.PrimaryTopBar
import com.android.taco.ui.theme.components.buttons.SecondaryButton
import com.android.taco.ui.theme.components.dialogBox.ErrorDialog
import com.android.taco.ui.theme.components.dialogBox.SuccessDialog
import com.android.taco.ui.theme.components.editTexts.EmailTextField
import com.android.taco.ui.theme.components.editTexts.PrimaryTextField
import com.android.taco.ui.theme.components.editTexts.UserNameTextField
import com.android.taco.ui.theme.components.image.CircularImageView
import com.android.taco.ui.theme.components.loadingBar.CircularProgress
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileEditScreen(navController: NavController,
                      viewModel: ProfileViewModel
) {
    var  showSuccessDialog by remember {
        mutableStateOf(false)
    }
    var  showErrorDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    TacoTheme() {
        Scaffold(topBar = {
            PrimaryTopBar(title = "Profilimi Düzenle") {
                navController.popBackStack()
            }
        }) {
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()) {
                Column( verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .padding(vertical = 12.dp, horizontal = 24.dp)

                ) {
                    CircularImageView(url = viewModel.ppUrl.value)
                    Column {
                        UserNameTextField(value = viewModel.username.value, label = "Kullanıcı Adı", placeholder = "Kullanıcı Adı", modifier = Modifier){name->
                            viewModel.username.value = name
                        }

                        UserNameTextField(value = viewModel.bio.value, label = "Bio", placeholder = "Bionuzu Giriniz", modifier = Modifier){bio->
                            viewModel.bio.value = bio
                        }

                        EmailTextField(value = FirebaseAuth.getInstance().currentUser?.email ?: "", label = "E-Posta", placeholder = "E-postanızı Giriniz", enabled = false){}
                    }

                    Column() {
                        SecondaryButton(text = "Güncelle") {
                            viewModel.updateProfile(){
                                if(it){
                                    showSuccessDialog = true
                                }else{
                                    showErrorDialog = true
                                }
                            }
                        }

                        SecondaryButton(text = "Oturumu Kapat", modifier = Modifier.padding(vertical = 12.dp)) {
                            viewModel.sigOut(){
                                AccountActivity.start(context = context)
                            }
                        }
                    }


                }

                if(viewModel.isLoading.value){
                    CircularProgress()
                }
                if (showSuccessDialog) {
                    SuccessDialog(
                        message = "Profil ayarları başarıyla güncellendi"
                    ) {
                        showSuccessDialog = false
                    }
                }
                if (showErrorDialog) {
                    ErrorDialog(
                        message = "İşlem sırasında bir hata oluştu"
                    ) {
                        showErrorDialog = false
                    }
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileEditScreenPreview(){
    ProfileEditScreen(navController = rememberNavController(), viewModel = viewModel())
}