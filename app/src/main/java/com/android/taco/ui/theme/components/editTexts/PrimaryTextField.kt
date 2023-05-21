package com.android.taco.ui.theme.components.editTexts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrimaryTextField(label : String, placeholder: String) {
    Column(horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = label,
            color = Color(0xff0a2533),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium)

        )
        Spacer(
            modifier = Modifier
                .height(height = 12.dp))
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(placeholder) },
            textStyle = TextStyle(
                fontSize = 16.sp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color(0xff97a2b0),
                backgroundColor = Color.Transparent),
            modifier = Modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryTextFieldPreview(){
    PrimaryTextField(label = "E-Posta", placeholder = "E-Posta adresinizi girin")
}