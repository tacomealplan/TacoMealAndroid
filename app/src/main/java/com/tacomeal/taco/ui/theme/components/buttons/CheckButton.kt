package com.tacomeal.taco.ui.theme.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tacomeal.taco.ui.theme.BrandSecondary
import com.tacomeal.taco.ui.theme.Green

@Composable
fun CheckButton(size: Int = 28, onClick : () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .size(size = size.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(8.dp))
                .clickable {
                    onClick.invoke()
                }
                .background(color = BrandSecondary)){
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Check",
                tint = Color.White,
                modifier = Modifier
                    .size(size = (size*0.85).dp)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CheckButtonPreview(){
    CheckButton(){}
}