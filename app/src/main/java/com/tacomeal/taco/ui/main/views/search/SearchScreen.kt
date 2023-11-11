package com.tacomeal.taco.ui.main.views.search

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tacomeal.taco.model.Recipe
import com.tacomeal.taco.ui.main.ScreensNavItem
import com.tacomeal.taco.ui.main.views.chef.CategoriesWidget
import com.tacomeal.taco.ui.main.views.chef.MealWidget
import com.tacomeal.taco.ui.main.views.chef.recipe.RecipeItem
import com.tacomeal.taco.ui.theme.BrandPrimary
import com.tacomeal.taco.ui.theme.BrandSecondary
import com.tacomeal.taco.ui.theme.components.buttons.FilterButton
import com.tacomeal.taco.ui.theme.components.buttons.SecondaryButton
import com.tacomeal.taco.ui.theme.components.editTexts.SearchTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel,
    forSelectingRecipes: Boolean = false,
    onRecipeSelected: ((recipe: Recipe) -> Unit)? = null
) {
    var searchText by remember { viewModel.searchText }
    var filterMeal by remember { viewModel.filterMeal }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllRecipes()
    })

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetElevation = 9.dp,
        sheetContent = { BottomSheet(viewModel = viewModel){
            coroutineScope.launch {
                sheetState.hide()
            }
        } },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                SearchTextField(value = searchText, placeholder = "Ara", modifier = Modifier.fillMaxWidth(0.8f), onValueChange = {value ->
                    searchText = value
                    if(searchText.isNotBlank() && !viewModel.isLoading.value)
                        viewModel.searchRecipesByFilter()
                })

                FilterButton {
                    coroutineScope.launch {
                        if (sheetState.isVisible) sheetState.hide()
                        else sheetState.show()
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .padding(start = 16.dp)
            ) {
                MealWidget(meals = viewModel.meals, showLabel = false, selectedMeal = filterMeal, onItemSelected = {selected ->
                    if(filterMeal == selected){
                        filterMeal = null
                    }else{
                        filterMeal = selected
                    }
                    viewModel.searchRecipesByFilter()
                })
            }

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
            ) {
                if(viewModel.isLoading.value){
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(color = BrandSecondary)
                    }
                }else{
                    if(viewModel.searchResults.isEmpty()){
                        Text(
                            text = "Aradağınız kriterlerde tarif bulunamadı",
                            color = BrandPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    viewModel.searchResults.forEach {
                        RecipeItem(recipe = it){
                            if(forSelectingRecipes){
                                onRecipeSelected?.invoke(it)
                            }else{
                                navController.navigate(ScreensNavItem.Recipe.screen_route + "/${it.id}")
                            }

                        }
                    }
                }

            }
        }
    }
}

@Composable
fun BottomSheet(viewModel: SearchViewModel, dissmis : () -> Unit) {
    var filterMeal by remember { viewModel.filterMeal }
    val filterCategories = remember { viewModel.filterCategories }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Filtre",
            color = BrandPrimary,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))

        MealWidget(meals = viewModel.meals, showLabel = false, selectedMeal = filterMeal, onItemSelected = {selected ->
            if(filterMeal == selected){
                filterMeal = null
            }else{
                filterMeal = selected
            }
            viewModel.searchRecipesByFilter()
        })
        Spacer(modifier = Modifier.height(12.dp))

        CategoriesWidget(viewModel.categories, selectedItems = filterCategories){category->
            if(filterCategories.contains(category))
                filterCategories.remove(category)
            else
                filterCategories.add(category)
            viewModel.searchRecipesByFilter()
        }
        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxWidth()) {
            SecondaryButton(text = "Filtreyi Uygula", modifier = Modifier.fillMaxWidth(0.5f)) {
                viewModel.searchRecipesByFilter()
                dissmis.invoke()
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(height = 54.dp)
                    .width(width = 327.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .clickable {
                        filterMeal = null
                        filterCategories.clear()
                        viewModel.searchRecipesByFilter()
                    }
            ) {
                Text(
                    text = "Filtreyi Temizle",
                    color = BrandSecondary,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold))
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview(){
    SearchScreen(navController = rememberNavController(), viewModel = viewModel())
}