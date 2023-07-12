package com.android.taco.ui.main.views.chef.recipe.edit

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.taco.ui.main.ScreensNavItem
import com.android.taco.ui.main.views.chef.MealWidget
import com.android.taco.ui.theme.components.bars.PrimaryTopBar
import com.android.taco.ui.theme.components.buttons.CardButton
import com.android.taco.ui.theme.components.buttons.SecondaryButton
import com.android.taco.ui.theme.components.dialogBox.CategorySelectionDialog
import com.android.taco.ui.theme.components.editTexts.PrimaryTextField

@Composable
fun EditRecipeScreen(navController: NavController, 
                     recipeId : String, 
                     viewModel: EditRecipeViewModel
){
    var openCategorySelection by remember{
        mutableStateOf(false)
    }
    var recipeName by remember{
        mutableStateOf(viewModel.recipe.value?.name)
    }
    var recipeDesc by remember{
        mutableStateOf(viewModel.recipe.value?.description)
    }

    LaunchedEffect(key1 = Unit, block = {
        if(recipeId.isEmpty()){
            viewModel.createNewRecipe()
        }else{
            viewModel.getRecipeInfo()
        }
    })

    Scaffold(topBar = {
        PrimaryTopBar(title = if(recipeId.isEmpty()) "Tarif Ekle" else "Tarif Düzenle") {
            navController.popBackStack()
        }
    }) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(it)
                    .padding(24.dp)
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {
                Column() {
                    PrimaryTextField(
                        value = recipeName ?: "",
                        placeholder = "Adı (Zorunlu Alan)",
                        onValueChange = {name->
                            recipeName = name
                        }
                    )

                    PrimaryTextField(
                        value = recipeDesc ?: "",
                        placeholder = "Açıklaması (Zorunlu Alan)",
                        onValueChange = {desc->
                            recipeDesc = desc
                        }
                    )

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()) {

                        PrimaryTextField(
                            value = viewModel.recipe.value?.description ?: "",
                            placeholder = "Süre (Zorunlu Alan)",
                            onValueChange = {dur->
                                viewModel.recipe.value?.duration = dur.toInt()
                            },
                            modifier = Modifier.fillMaxWidth(0.5f)
                        )

                        PrimaryTextField(
                            value = viewModel.recipe.value?.personCount.toString() ?: "",
                            placeholder = "Kişi Sayısı (Zorunlu Alan)",
                            onValueChange = {count->
                                viewModel.recipe.value?.personCount = count.toInt()
                            },
                            modifier = Modifier.fillMaxWidth(1f)
                        )


                    }

                }

                MealWidget(selectedMeal = null){

                }

                PrimaryTextField(
                    value = "",
                    label = "Kategoriler",
                    placeholder = "Kategoriler",
                    enabled = false,
                    onValueChange={},
                    modifier = Modifier.clickable{
                        openCategorySelection = true
                    }
                )

                CardButton(text = "Adımlar & Malzemeler", modifier = Modifier.padding(vertical = 12.dp)) {
                    navController.navigate(ScreensNavItem.EditRecipeDetail.screen_route)
                }

                Spacer(modifier = Modifier.height(12.dp))
                SecondaryButton(text = "Kaydet") {
                    viewModel.createNewRecipe()
                }



            }

            if(openCategorySelection){
                CategorySelectionDialog(items= viewModel.allCategories.toList() as ArrayList<String>,
                    selectedItems = arrayListOf(),
                    onItemClicked = {
                        viewModel.selectedCategories.add(it)
                    },
                    onDismiss = { openCategorySelection = false },
                    onSaved = { item ->
                        openCategorySelection = false
                })
            }
        }

    }


}

@Preview
@Composable
fun EditRecipeScreenPreview(){
    //EditRecipeScreen()
}