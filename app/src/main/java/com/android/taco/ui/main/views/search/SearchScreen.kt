package com.android.taco.ui.main.views.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.android.taco.ui.main.views.chef.CategoriesWidget
import com.android.taco.ui.main.views.chef.MealWidget
import com.android.taco.ui.main.views.chef.recipe.RecipeItem
import com.android.taco.ui.main.views.populars.PopularsScreen
import com.android.taco.ui.main.views.populars.PopularsWidget
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.BrandSecondary
import com.android.taco.ui.theme.White
import com.android.taco.ui.theme.components.buttons.FilterButton
import com.android.taco.ui.theme.components.buttons.PrimaryButton
import com.android.taco.ui.theme.components.buttons.SecondaryButton
import com.android.taco.ui.theme.components.editTexts.SearchTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(navController: NavController,
                 viewModel: SearchViewModel
) {
    var searchText by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetElevation = 9.dp,
        sheetContent = { BottomSheet(viewModel = viewModel) },
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
                        viewModel.searchRecipeByText(searchText)
                })

                FilterButton {
                    coroutineScope.launch {
                        if (sheetState.isVisible) sheetState.hide()
                        else sheetState.show()
                    }
                }
            }
            if(searchText.isBlank()){
                Row(modifier = Modifier.padding(horizontal = 24.dp)) {
                    PopularsWidget(navController)
                }
            }else{
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
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
                            RecipeItem(recipe = it){}
                        }
                    }

                }
            }





        }
    }
}

@Composable
fun BottomSheet(viewModel: SearchViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(24.dp)
    ) {
        Text(
            text = "Filtre",
            color = BrandPrimary,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)
        )

        MealWidget(){}
        Spacer(modifier = Modifier.height(24.dp))
        CategoriesWidget(viewModel.categories, selectedItems = listOf()){}
        Spacer(modifier = Modifier.height(24.dp))

        SecondaryButton(text = "Filtreyi Uygula") {

        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(height = 54.dp)
                .width(width = 327.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .clickable {

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

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview(){
    SearchScreen(navController = rememberNavController(), viewModel = viewModel())
}