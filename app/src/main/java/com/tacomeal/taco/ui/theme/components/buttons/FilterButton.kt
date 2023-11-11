package com.tacomeal.taco.ui.theme.components.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tacomeal.taco.R
import com.tacomeal.taco.ui.theme.BrandSecondary
import com.tacomeal.taco.ui.theme.components.editTexts.PasswordTextField

@Composable
fun FilterButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = BrandSecondary),
        modifier = Modifier
            .size(80.dp)
            .padding(all = 12.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.filter),
            tint = Color.White,
            contentDescription = "Iconly/Light/Filter")

    }
}

@Preview(showBackground = true)
@Composable
fun FilterButtonPreview(){
    FilterButton(){}
}