package com.tacomeal.taco.ui.main.views.chef.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.tacomeal.taco.R
import com.tacomeal.taco.model.Recipe
import com.tacomeal.taco.ui.theme.BrandPrimary
import com.tacomeal.taco.ui.theme.components.buttons.RightArrowButton

@Composable
fun RecipeItem(recipe : Recipe, onClick : () -> Unit){
    var coverPhotoUrl by remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit){
        getUrlForStorage(recipe.coverPhotoLink ?: ""){
            coverPhotoUrl = it
        }
    }

    Surface(
        elevation = 9.dp, // play with the elevation values
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(4.dp).clickable {
            onClick.invoke()
        }
    ){
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(100.dp).padding(12.dp)
        ) {
            Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.33f)) {
                Image(
                    painter = rememberImagePainter(
                        data = coverPhotoUrl,
                        builder = {
                            crossfade(false)
                            placeholder(R.color.imagePlaceholderColor)
                        }
                    ),
                    contentDescription = "description",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomStart = 16.dp,
                                bottomEnd = 16.dp
                            )
                        )
                )
            }

            Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.8f).padding(8.dp)) {
                Text(
                    text = recipe.name,
                    color = BrandPrimary,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold),
                    modifier = Modifier
                )

                Text(
                    text = recipe.creatorUserName,
                    color = Color(0xff97a2b0).copy(alpha = 0.75f),
                    lineHeight = 145.sp,
                    style = TextStyle(
                        fontSize = 14.sp))


            }
            Column(modifier = Modifier.fillMaxWidth(1f)) {
                RightArrowButton {

                }
            }

        }
    }
}

@Preview
@Composable
fun RecipeItemPreview(){
    RecipeItem(Recipe.dummyInstance()){}
}