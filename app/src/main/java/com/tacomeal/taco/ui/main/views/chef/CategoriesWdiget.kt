package com.tacomeal.taco.ui.main.views.chef

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tacomeal.taco.ui.main.views.chef.recipe.RecipeCard
import com.tacomeal.taco.ui.theme.BrandPrimary
import com.tacomeal.taco.ui.theme.BrandSecondary
import com.tacomeal.taco.ui.theme.NeutralGray3
import com.tacomeal.taco.ui.theme.NeutralGray4

@Composable
fun CategoriesWidget(categories : List<String>, selectedItems: List<String> = ArrayList(), onItemSelected : (item : String) -> Unit){
    Column(modifier = Modifier
        .fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .scrollable(state = rememberScrollState(), orientation = Orientation.Horizontal)
        ) {
            Text(
                text = "Kategoriler",
                color = BrandPrimary,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold),
                modifier = Modifier
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        LazyHorizontalGrid(
            rows = GridCells.Fixed(3),
            modifier = Modifier.height(136.dp)

        ) {
            items(categories) {item->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .wrapContentHeight()
                        .clip(shape = RoundedCornerShape(40.dp))
                        .background(color = if (selectedItems.contains(item)) BrandSecondary else NeutralGray3)
                        .clickable {
                            onItemSelected.invoke(item)
                        }
                ) {
                    Text(
                        text = item,
                        color = if(selectedItems.contains(item)) Color.White else BrandPrimary,
                        modifier = Modifier.padding(8.dp),
                        style = TextStyle(
                            fontSize = 16.sp))
                }
            }
        }

        /*Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .horizontalScroll(state = rememberScrollState())
                .fillMaxWidth()
        ){
            List(categories = categories, selectedItems = selectedItems.toList()){
                onItemSelected.invoke(it)

            }
        }*/

    }
}

@Composable
private fun List(categories: List<String>, selectedItems: List<String> = ArrayList(), onItemSelected : (item : String) -> Unit) {
    Row(modifier = Modifier
    ) {
        categories.forEach { item ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clip(shape = RoundedCornerShape(40.dp))
                    .background(color = if (selectedItems.contains(item)) BrandSecondary else NeutralGray3)
                    .clickable {
                        onItemSelected.invoke(item)
                    }
            ) {
                Text(
                    text = item,
                    color = if(selectedItems.contains(item)) Color.White else BrandPrimary,
                    modifier = Modifier.padding(8.dp),
                    style = TextStyle(
                        fontSize = 16.sp))
            }
        }
    }
}

@Preview
@Composable
fun CategoriesPreview(){
    CategoriesWidget(listOf("Dünya Mutfakları","Diyet", "Et Yemeği"), selectedItems = listOf("Diyet")){}
}