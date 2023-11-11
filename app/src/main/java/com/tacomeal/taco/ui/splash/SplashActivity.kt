package com.tacomeal.taco.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.lifecycleScope
import com.tacomeal.taco.R
import com.tacomeal.taco.data.SharedPref
import com.tacomeal.taco.ui.account.AccountActivity
import com.tacomeal.taco.ui.main.MainActivity
import com.tacomeal.taco.ui.splash.onboarding.OnBoarding
import com.tacomeal.taco.ui.theme.BrandPrimary
import com.tacomeal.taco.ui.theme.BrandSecondary
import com.tacomeal.taco.ui.theme.TacoTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var skipOnboarding by remember { mutableStateOf<Boolean?>(null) }
            var intent by remember { mutableStateOf(Intent(this@SplashActivity, AccountActivity::class.java)) }
            LaunchedEffect(key1 = Unit, block = {
                delay(3000)
                skipOnboarding = com.tacomeal.taco.data.SharedPref.invoke().getSkipOnboarding()
            })


            LaunchedEffect(Unit) {
                if (FirebaseAuth.getInstance().currentUser != null){
                    intent = Intent(this@SplashActivity, MainActivity::class.java)
                }
            }
            TacoTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BrandSecondary
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(painter = painterResource(id = R.drawable.splash_image), contentDescription = "", modifier = Modifier.fillMaxSize())
                    }
                    if(skipOnboarding == true){
                        startActivity(intent)
                    }
                    if(skipOnboarding == false){
                        OnBoarding(toMainView = {
                            startActivity(intent)
                        })
                    }

                }
            }
        }
    }
}