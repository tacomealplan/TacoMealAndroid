package com.android.taco.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.android.taco.ui.account.AccountActivity
import com.android.taco.ui.splash.onboarding.OnBoarding
import com.android.taco.ui.theme.TacoTheme

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
            TacoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    OnBoarding(toMainView = {
                        val intent = Intent(this@SplashActivity, AccountActivity::class.java)
                        startActivity(intent)
                    })
                }
            }
        }
    }
}