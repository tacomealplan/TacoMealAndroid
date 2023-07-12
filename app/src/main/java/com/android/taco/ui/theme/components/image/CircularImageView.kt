package com.android.taco.ui.theme.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.android.taco.R
import com.android.taco.ui.theme.BrandSecondary

@Composable
fun CircularImageView(size : Int = 120){
    Image(
        painter = painterResource(R.drawable.ic_launcher_background),
        contentDescription = "avatar",
        contentScale = ContentScale.Crop,            // crop the image if it's not a square
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)                       // clip to the circle shape
            .border(2.dp, BrandSecondary, CircleShape)   // add a border (optional)
    )
}

@Composable
fun CircularImageView(url : String, size : Int = 120){
    Image(
        painter = rememberImagePainter(
            data = url,
            builder = {
                crossfade(false)
                placeholder(R.color.imagePlaceholderColor)
            }
        ),
        contentDescription = "avatar",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)                       // clip to the circle shape
            .border(1.dp, BrandSecondary, CircleShape)   // add a border (optional)
    )
}

@Preview
@Composable
fun CircularImagePreview(){
    CircularImageView()
}