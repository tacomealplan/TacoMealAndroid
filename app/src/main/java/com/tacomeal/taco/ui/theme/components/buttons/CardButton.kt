package com.tacomeal.taco.ui.theme.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tacomeal.taco.ui.theme.components.image.CircularImageView

@Composable
fun CardButton(
    text : String,
    modifier: Modifier = Modifier,
    onClick : () -> Unit
) {
    Surface(
        elevation = 9.dp, // play with the elevation values
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    onClick.invoke()
                }
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            Row() {
                Column() {
                    Text(
                        text = text,
                        color = Color(0xff0a2533),
                        lineHeight = 110.sp,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold)
                    )
                }
            }


            RightArrowButton {
                onClick.invoke()
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun CardButtonPreview(){
    CardButton(text = "Test"){}
}