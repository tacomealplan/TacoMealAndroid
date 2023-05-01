package com.base.app.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.base.app.R
import com.base.app.ui.account.AccountActivity
import com.base.app.ui.main.MainActivity
import com.base.app.ui.splash.onboarding.OnBoarding
import com.base.app.ui.theme.TacoTheme
import kotlinx.coroutines.delay

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