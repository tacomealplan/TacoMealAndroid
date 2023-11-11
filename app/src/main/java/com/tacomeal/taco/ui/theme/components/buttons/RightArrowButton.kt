package com.tacomeal.taco.ui.theme.components.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tacomeal.taco.R
import com.tacomeal.taco.ui.theme.BrandPrimary
import com.tacomeal.taco.ui.theme.components.cards.ProfileCardView

@Composable
fun RightArrowButton(size: Int = 28, onClick : () -> Unit) {
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
                .background(color = BrandPrimary)){
            Icon(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "Iconly/Light/Arrow - Right",
                tint = Color.White,
                modifier = Modifier
                    .size(size = (size*0.55).dp)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RightArrowButtonPreview(){
    RightArrowButton(){}
}