package com.android.taco.ui.main.views.chef.recipe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.android.taco.R
import com.android.taco.model.Recipe
import com.android.taco.ui.theme.BrandSecondary
import com.android.taco.ui.theme.components.image.CircularImageView
import com.google.firebase.storage.FirebaseStorage

@Composable
fun RecipeCardById(recipeId: String, viewModel: RecipeByIdViewModel, onClick: () -> Unit) {
    val coverPhotoUrl by remember {
        viewModel.coverPhotoUrl
    }
    val recipe : Recipe? by remember {
        viewModel.recipe
    }
    LaunchedEffect(Unit){
        viewModel.getRecipeById(recipeId = recipeId)
    }

    Surface(
        elevation = 9.dp, // play with the elevation values
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(4.dp)
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding()
                .clickable {
                    onClick.invoke()
                }
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

                if(viewModel.isLoading.value){
                    CircularProgressIndicator(color = BrandSecondary)
                }else{
                    Box(modifier = Modifier
                        .width(width = 132.dp)
                        .height(height = 88.dp)
                        .background(shape = RoundedCornerShape(16.dp), color = Color.White)
                    ) {
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
                                .clip(shape = RoundedCornerShape(
                                    topStart = 16.dp,
                                    topEnd = 16.dp,
                                    bottomStart = 16.dp,
                                    bottomEnd = 16.dp))
                        )
                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                        ) {
                            ButtonLike(){}
                        }
                    }

                    Text(
                        text = recipe?.name ?: "",
                        color = Color(0xff0a2533),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        Spacer(modifier = Modifier.width(4.dp))
                        recipe?.creatorPhotoURL?.let { CircularImageView(url = it, size = 28) }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = recipe?.creatorUserName ?: "",
                            color = Color(0xff97a2b0).copy(alpha = 0.75f),
                            style = TextStyle(
                                fontSize = 14.sp)
                        )
                    }

                }
            }
        }
    }

}
@Preview
@Composable
fun RecipeCardByIdPreview(){
    RecipeCardById(recipeId = "", viewModel = viewModel()){}
}