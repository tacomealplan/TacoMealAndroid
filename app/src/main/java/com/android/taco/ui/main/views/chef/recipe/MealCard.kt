package com.android.taco.ui.main.views.chef.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taco.R
import com.android.taco.ui.theme.BrandSecondary

@Composable
fun MealCard(){
    Surface(
        elevation = 9.dp, // play with the elevation values
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        Column(modifier = Modifier
            .background(color = BrandSecondary)
            .size(width = 265.dp, height = 172.dp)) {
            Spacer(modifier = Modifier.fillMaxHeight(0.5f))
            Text(
                text = "Deniz Mahsüllü \nNoodle",
                color = Color.White,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .width(width = 189.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.love),
                        contentDescription = "Iconly/Light/Time Circle",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(size = 20.dp))
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = "Ceren Taşsın",
                        color = Color.White.copy(alpha = 0.75f),
                        style = TextStyle(
                            fontSize = 14.sp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.time_circle),
                        contentDescription = "Iconly/Light/Time Circle",
                        modifier = Modifier
                            .size(size = 16.dp))
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = "20 dk",
                        color = Color.White.copy(alpha = 0.75f),
                        style = TextStyle(
                            fontSize = 14.sp))
                }

            }
        }
    }
}


@Preview
@Composable
fun MealCardPreview(){
    MealCard()
}