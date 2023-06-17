package com.android.taco.ui.theme.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taco.ui.main.views.search.SearchScreen
import com.android.taco.ui.theme.components.buttons.RightArrowButton
import com.android.taco.ui.theme.components.image.CircularImageView

@Composable
fun ProfileCardView(modifier: Modifier = Modifier ,onClick : () -> Unit) {
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
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Row() {
                CircularImageView(size = 48)
                Spacer(modifier = Modifier.width(16.dp))
                Column() {
                    Text(
                        text = "Alena Sabyan",
                        color = Color(0xff0a2533),
                        lineHeight = 110.sp,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Recipe Developer",
                        color = Color(0xff48525f),
                        lineHeight = 145.sp,
                        style = TextStyle(
                            fontSize = 14.sp))
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
fun ProfileCardPreview(){
    ProfileCardView(){}
}