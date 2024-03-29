package com.tacomeal.taco.ui.theme.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tacomeal.taco.ui.theme.BrandSecondary
import com.tacomeal.taco.ui.theme.TacoTheme

@Composable
fun SecondaryButton(text : String,
                    modifier: Modifier = Modifier,
                    onClick : () -> Unit
){
    TacoTheme() {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = BrandSecondary),
            border = BorderStroke(1.dp, BrandSecondary),
            modifier = modifier
                .width(width = 327.dp)
                .height(height = 54.dp)
        ) {
            Text(
                text = text,
                color = Color.White,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold))
        }
    }
}

@Preview
@Composable
fun SecondaryButtonPreview(){
    SecondaryButton(text = "Giriş", onClick = {})
}