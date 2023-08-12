package com.android.taco.ui.main.views.chef.plan

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taco.ui.main.views.chef.recipe.MealCard
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.NeutralGray2
import com.android.taco.ui.theme.NeutralGray4
import com.android.taco.ui.theme.components.loadingBar.CircularProgress

@Composable
fun DailyMenuWidget(planId: String,
                    viewModel: PlanViewModel,
                    onRecipeSelected : (recipeId : String) -> Unit
) {
    val dailyRecipes = remember {
        viewModel.dailyRecipes
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getDailyRecipes(planId = planId)
    })
    Column(modifier = Modifier
        .fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Günlük Menü",
                color = BrandPrimary,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold),
                modifier = Modifier
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if(viewModel.isLoading.value){
            CircularProgress()
        }else{
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .fillMaxWidth()
            ){
                dailyRecipes.forEach {
                    MealCard(recipe = it){recipeId ->
                        onRecipeSelected.invoke(recipeId)
                    }
                }
                if(dailyRecipes.isEmpty()){
                    Surface(shape = RoundedCornerShape(8.dp), color = NeutralGray4) {
                        Text(text = "Bugün için eklenmiş bir tarif bulunmamaktadır",
                            modifier = Modifier.padding(4.dp)
                        )
                    }

                }
            }
        }
    }
}