package com.android.taco.ui.main.views.chef.recipe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taco.R

@Composable
fun RecipeCard() {
    Surface(
        elevation = 9.dp, // play with the elevation values
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(4.dp)
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(width = 156.dp)
                    .height(height = 198.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(color = Color.White)
                    .border(border = BorderStroke(1.dp, Color(0xfffbfbfb)))){
                Box(modifier = Modifier
                    .width(width = 132.dp)
                    .height(height = 88.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Image 1",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize())
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.fillMaxSize().padding(4.dp)
                    ) {
                        ButtonLike(){}
                    }
                }

                Text(
                    text = "Sunny Egg & Toast Avocado",
                    color = Color(0xff0a2533),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp))
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Alice Fala",
                    color = Color(0xff97a2b0).copy(alpha = 0.75f),
                    style = TextStyle(
                        fontSize = 14.sp)
                )
            }





        }
    }

}

@Composable
fun ButtonLike(onClick : () -> Unit) {
    Column(
        modifier = Modifier
            .width(width = 28.dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(size = 28.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = Color.White)){
            Image(
                painter = painterResource(id = R.drawable.heart),
                contentDescription = "Iconly/Bold/Heart",
                colorFilter = ColorFilter.tint(Color(0xffff8c00)),
                modifier = Modifier
                    .size(size = 17.dp))
        }

    }
}

@Preview
@Composable
fun RecipeCardPreview(){
    RecipeCard()
}