package com.android.taco.ui.main.views.chef.planList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.android.taco.model.Plan
import com.android.taco.model.Recipe
import com.android.taco.ui.main.views.chef.plan.PlanItem
import com.android.taco.ui.main.views.chef.recipe.RecipeCard
import com.android.taco.ui.theme.TacoTheme

@Composable
fun PlanListScreen(planList: List<Plan>, onPlanSelected : (plan : Plan) -> Unit){
    TacoTheme() {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .background(Color.White)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier

            ) {
                items(planList) {plan ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        PlanItem(plan = plan) {
                            onPlanSelected.invoke(plan)
                        }
                    }
                }
            }
        }

    }
}