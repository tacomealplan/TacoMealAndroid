package com.android.taco.ui.theme.components.bars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taco.ui.theme.BrandPrimary

@Composable
fun PrimaryTopBar(title : String, onBackPressed : ()-> Unit){
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.padding(vertical = 12.dp)
    ){
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        ) {
            Text(
                text = title,
                color = BrandPrimary,
                textAlign = TextAlign.Center,
                lineHeight = 135.sp,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold)
            )
        }
        Row(horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        ){
            Icon(
                Icons.Rounded.ArrowBack, contentDescription = "Localized description",
                modifier = Modifier.clickable {
                    onBackPressed.invoke()
                })
        }

    }
}

@Preview
@Composable
fun PrimaryTopBarPreview(){
    PrimaryTopBar(title = "Başlık"){}
}


