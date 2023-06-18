package com.android.taco.ui.main.views.chef.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.taco.ui.theme.components.buttons.RightArrowButton

@Composable
fun RecipeItem(){
    Surface(
        elevation = 9.dp, // play with the elevation values
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(4.dp)
    ){
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(100.dp).padding(12.dp)
        ) {
            Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.33f)) {
                
            }

            Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.8f)) {
                Text("Denem")
                Text("Denem")
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
    RecipeItem()
}