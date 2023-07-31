package com.android.taco.ui.main.views.chef.plan.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.taco.model.Plan
import com.android.taco.ui.main.ScreensNavItem
import com.android.taco.ui.main.views.chef.plan.Day
import com.android.taco.ui.main.views.chef.plan.DayList
import com.android.taco.ui.main.views.chef.plan.PlanViewModel
import com.android.taco.ui.main.views.chef.recipe.RecipeCardById
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.BrandSecondary
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.bars.PrimaryTopBar
import com.android.taco.ui.theme.components.bars.TitleTopBar
import com.android.taco.ui.theme.components.buttons.PrimaryButton
import com.android.taco.ui.theme.components.buttons.SecondaryButton
import com.android.taco.ui.theme.components.dialogBox.CategorySelectionDialog
import com.android.taco.ui.theme.components.editTexts.PrimaryTextField

@Composable
fun PlanInfoEditScreen(
               viewModel: PlanEditViewModel,
               navController: NavController
){
    LaunchedEffect(key1 = Unit, block = {

    })
    val recipeCategories = remember{ viewModel.selectedCategories }
    var planName by remember{ viewModel.planName }
    var planMotivation by remember{ viewModel.planMotivation }
    var openCategorySelection by remember{mutableStateOf(false) }

    TacoTheme() {
        Scaffold(topBar = {
            TitleTopBar(title = "Haftalık Plan Oluştur")
        }) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(it)
                        .verticalScroll(state = rememberScrollState())
                        .fillMaxHeight(0.9f)
                        .fillMaxWidth()) {

                    PrimaryTextField(
                        value = planName,
                        label = "Adı",
                        placeholder = "Plan adını giriniz",
                        onValueChange = {
                            planName = it
                        }
                    )

                    PrimaryTextField(
                        value = recipeCategories.joinToString("-"),
                        label = "Kategoriler",
                        placeholder = "Kategoriler",
                        enabled = false,
                        maxLines = 2,
                        onValueChange={},
                        modifier = Modifier.clickable{
                            openCategorySelection = true
                        }
                    )

                    PrimaryTextField(
                        value = planMotivation,
                        label = "Motivasyon",
                        placeholder = "Motivasyonunuzu Giriniz",
                        onValueChange = {
                            planMotivation = it
                        }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SecondaryButton(text = "Kaydet") {
                        
                    }
                }
            }
            if(openCategorySelection){
                CategorySelectionDialog(items= viewModel.allCategories.toList() as ArrayList<String>,
                    selectedItems = ArrayList(recipeCategories.toList()),
                    onItemClicked = {
                        if(recipeCategories.contains(it))
                            recipeCategories.remove(it)
                        else
                            recipeCategories.add(it)
                    },
                    onDismiss = { openCategorySelection = false },
                    onSaved = { item ->
                        openCategorySelection = false
                    })
            }
        }
    }
}
