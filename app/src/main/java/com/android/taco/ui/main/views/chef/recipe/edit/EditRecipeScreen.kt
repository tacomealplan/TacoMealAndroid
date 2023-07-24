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
import com.android.taco.ui.theme.components.loadingBar.CircularProgress

@Composable
fun EditRecipeScreen(navController: NavController, 
                     recipeId : String, 
                     viewModel: EditRecipeViewModel
){
    var openCategorySelection by remember{mutableStateOf(false) }
    var recipeName by remember{ viewModel.recipeName }
    var recipeDesc by remember{ viewModel.recipeDesc }
    var recipeMeal by remember{ viewModel.recipeMeal }
    var recipePersonCount by remember{ viewModel.recipePersonCount }
    var recipeDuration by remember{ viewModel.recipeDuration }
    val recipeCategories = remember{ viewModel.selectedCategories }

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
                        singleLine = true,
                        onValueChange = {name->
                            recipeName = name
                        }
                    )

                    PrimaryTextField(
                        value = recipeDesc ?: "",
                        placeholder = "Açıklaması (Zorunlu Alan)",
                        minLines = 3,
                        maxLines = 3,
                        onValueChange = {desc->
                            recipeDesc = desc
                        }
                    )

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {

                        PrimaryTextField(
                            value = try {
                                if(recipeDuration != null) recipeDuration.toString() else ""
                            } catch (e: Exception) {
                                ""
                            },
                            placeholder = "Süre (Zorunlu)",
                            onValueChange = { dur->
                                recipeDuration = try {
                                    dur.toInt()
                                } catch (e: Exception) {
                                    null
                                }
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.5f).height(100.dp)
                        )

                        PrimaryTextField(
                            value = try {
                                if(recipePersonCount != null) recipePersonCount.toString() else ""
                            } catch (e: Exception) {
                                ""
                            },
                            placeholder = "Kişi Sayısı (Zorunlu)",
                            onValueChange = {count->
                                recipePersonCount = try {
                                    count.toInt()
                                } catch (e: Exception) {
                                    null
                                }
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(1f).height(100.dp)
                        )

                    }

                }

                Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                    MealWidget(meals = viewModel.allMeals, selectedMeal = recipeMeal ){selectedMeal ->
                        recipeMeal = selectedMeal
                    }
                }


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

                CardButton(text = "Adımlar & Malzemeler", modifier = Modifier.padding(vertical = 12.dp)) {
                    navController.navigate(ScreensNavItem.EditRecipeDetail.screen_route)
                }

                Spacer(modifier = Modifier.height(12.dp))
                SecondaryButton(text = "Kaydet") {
                    if(viewModel.isNewInstance.value){
                        viewModel.insertRecipe(onSuccess = {

                        }, onError = {

                        })
                    }else{
                        viewModel.updateRecipe()
                    }

                }
            }

            if(viewModel.isLoading.value){
                CircularProgress()
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

@Preview
@Composable
fun EditRecipeScreenPreview(){
    //EditRecipeScreen()
}