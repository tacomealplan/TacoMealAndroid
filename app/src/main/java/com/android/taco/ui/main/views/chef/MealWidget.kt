package com.android.taco.ui.main.views.chef

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.taco.R
import com.android.taco.ui.main.ScreensNavItem
import com.android.taco.ui.main.views.chef.recipe.RecipeCard
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.BrandSecondary
import com.android.taco.ui.theme.NeutralGray4
import com.android.taco.ui.theme.White

@Composable
fun MealWidget(){
    Column(modifier = Modifier
        .fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().scrollable(state = rememberScrollState(), orientation = Orientation.Horizontal)
        ) {
            Text(
                text = "Öğünler",
                color = BrandPrimary,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold),
                modifier = Modifier
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth()
        ){
            List(selectedDay = Meal.Lunch){

            }
        }

    }
}

@Composable
fun List(selectedDay : Meal = Meal.Breakfast, onItemSelected : (meal : Meal) -> Unit) {
    Row(modifier = Modifier.scrollable(state = rememberScrollState(), orientation = Orientation.Horizontal)
    ) {
        Meal.values().forEach { meal ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clip(shape = RoundedCornerShape(40.dp))
                    .background(color = if (selectedDay == meal) BrandSecondary else NeutralGray4)
                    .clickable {
                        onItemSelected.invoke(meal)
                    }
            ) {
                Text(
                    text = meal.name,
                    color = if(selectedDay == meal) Color.White else BrandPrimary,
                    modifier = Modifier.padding(8.dp),
                    style = TextStyle(
                        fontSize = 16.sp))
            }
        }
    }
}

sealed class Meal(var name:String){
    object Breakfast : Meal("Kahvaltı")
    object Lunch : Meal("Öğle Yemeği")
    object Dinner : Meal("Akşam Yemeği")
    companion object {
        fun values() : ArrayList<Meal> {
            val result = ArrayList<Meal>()
            result.add(Breakfast)
            result.add(Lunch)
            result.add(Dinner)
            return result

        }
    }
}

@Preview
@Composable
fun MealWidgetPreview(){
    MealWidget()
}