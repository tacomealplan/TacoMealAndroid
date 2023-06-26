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
import coil.compose.rememberImagePainter
import com.android.taco.R
import com.android.taco.model.Recipe
import com.google.firebase.storage.FirebaseStorage

@Composable
fun RecipeCard(recipe: Recipe, onClick: () -> Unit) {
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
                    text = recipe.name ?: "",
                    color = Color(0xff0a2533),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp))
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = recipe.creatorUserName ?: "",
                    color = Color(0xff97a2b0).copy(alpha = 0.75f),
                    style = TextStyle(
                        fontSize = 14.sp)
                )
            }
        }
    }

}

fun getUrlForStorage(path : String, url : (url : String) -> Unit){
    if(path.isBlank()) {
        url.invoke("")
        return
    }

    val storageRef = FirebaseStorage.getInstance().reference
    storageRef.child(path).downloadUrl.addOnSuccessListener {
        url.invoke(it.toString())
        // Got the download URL for 'users/me/profile.png'
    }.addOnFailureListener {
        // Handle any errors
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
    RecipeCard(recipe = Recipe.dummyInstance()){}
}