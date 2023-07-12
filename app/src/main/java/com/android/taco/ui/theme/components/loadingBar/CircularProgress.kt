package com.android.taco.ui.theme.components.loadingBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.android.taco.ui.theme.BrandSecondary

@Composable
fun CircularProgress(){
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(
            indication = null, // disable ripple effect
            interactionSource = remember { MutableInteractionSource() },
            onClick = { }
        ),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = BrandSecondary)
    }
}