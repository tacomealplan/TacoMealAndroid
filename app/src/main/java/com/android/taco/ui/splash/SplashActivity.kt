package com.android.taco.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.android.taco.data.SharedPref
import com.android.taco.ui.account.AccountActivity
import com.android.taco.ui.main.MainActivity
import com.android.taco.ui.splash.onboarding.OnBoarding
import com.android.taco.ui.theme.BrandSecondary
import com.android.taco.ui.theme.TacoTheme
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*lifecycleScope.launchWhenCreated {
            delay(3000)

            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }*/
        setContent {
            var skipOnboarding by remember { mutableStateOf<Boolean?>(null) }
            var intent by remember { mutableStateOf(Intent(this@SplashActivity, AccountActivity::class.java)) }

            LaunchedEffect(Unit) {
                if (FirebaseAuth.getInstance().currentUser != null){
                    intent = Intent(this@SplashActivity, MainActivity::class.java)
                }

                skipOnboarding = SharedPref.invoke().getSkipOnboarding()

            }
            TacoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BrandSecondary
                ) {
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