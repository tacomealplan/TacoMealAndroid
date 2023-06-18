package com.android.taco.ui.main.views.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.taco.R
import com.android.taco.ui.main.views.search.SearchScreen
import com.android.taco.ui.theme.BrandSecondary
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.bars.PrimaryTopBar
import com.android.taco.ui.theme.components.buttons.SecondaryButton
import com.android.taco.ui.theme.components.cards.ProfileCardView
import com.android.taco.ui.theme.components.editTexts.EmailTextField
import com.android.taco.ui.theme.components.editTexts.PrimaryTextField
import com.android.taco.ui.theme.components.image.CircularImageView
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileEditScreen(navController: NavController,
                      viewModel: ProfileViewModel
) {
    TacoTheme() {
        Scaffold(topBar = {
            PrimaryTopBar(title = "Profilimi Düzenle") {
                navController.popBackStack()
            }
        }) {
            Column( verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(vertical = 12.dp, horizontal = 24.dp)

            ) {
                CircularImageView(url = viewModel.ppUrl.value)
                Column {
                    Row {
                        PrimaryTextField(value = viewModel.username.value, label = "Kullanıcı Adı", placeholder = "Kullanıcı Adı", modifier = Modifier){
                            viewModel.username.value = it
                        }
                    }

                    PrimaryTextField(value = "", label = "Bio", placeholder = "Bionuzu Giriniz", modifier = Modifier){

                    }

                    EmailTextField(value = FirebaseAuth.getInstance().currentUser?.email ?: "", label = "E-Posta", placeholder = "E-postanızı Giriniz", enabled = false){

                    }
                }

                Column() {
                    SecondaryButton(text = "Güncelle") {
                        viewModel.updateProfile()
                    }

                    SecondaryButton(text = "Oturumu Kapat", modifier = Modifier.padding(vertical = 12.dp)) {
                        viewModel.sigOut()
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