package com.android.taco.ui.main.views.chef.recipe.detail

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.android.taco.R
import com.android.taco.model.Material
import com.android.taco.model.Step
import com.android.taco.ui.main.views.chef.materials.MaterialListScreen
import com.android.taco.ui.main.views.chef.materials.MaterialRow
import com.android.taco.ui.main.views.chef.steps.StepListScreen
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.NeutralGray2
import com.android.taco.ui.theme.NeutralGray4
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.components.image.CircularImageView
import com.android.taco.ui.theme.components.tabs.TabsTwoOptions

@Composable
fun RecipeScreen(recipeId : String,
                 navController: NavController,
                 viewModel: RecipeScreenViewModel){
    var coverPhotoUrl by remember {
        viewModel.coverPhotoUrl
    }

    var selectedTab by remember {
        mutableStateOf(0)
    }
    val recipe by remember {
        viewModel.recipe
    }
    val recipeDetail by remember {
        viewModel.recipeDetail
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.getRecipeById(recipeId)
    })

    TacoTheme() {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            if(viewModel.isLoading.value){
                CircularProgressIndicator()
            }else{
                Image(
                    painter = rememberImagePainter(
                        data = coverPhotoUrl,
                        builder = {
                            crossfade(false)
                            placeholder(R.color.imagePlaceholderColor)
                        }
                    ),
                    contentDescription = "description",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .verticalScroll(state = rememberScrollState())
                        .fillMaxSize()
                        .padding(24.dp)
                        .background(color = Color.White)
                ){

                    Text(
                        text = recipe?.name ?: "",
                        color = BrandPrimary,
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(
                                color = Color(0xff748189),
                                fontSize = 16.sp)
                            ) {append(recipe?.description ?: "")}
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .width(width = 71.dp)
                                .height(height = 20.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.time_circle),
                                tint = NeutralGray2,
                                contentDescription = "Iconly/Light/Time Circle")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${recipe?.personCount} Kişilik",
                                color = Color(0xff748189),
                                style = TextStyle(
                                    fontSize = 14.sp))
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .width(width = 71.dp)
                                .height(height = 20.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.time_circle),
                                tint = NeutralGray2,
                                contentDescription = "Iconly/Light/Time Circle")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${recipe?.duration} dk",
                                color = Color(0xff748189),
                                style = TextStyle(
                                    fontSize = 14.sp))
                        }
                    }

                    TabsTwoOptions(selectedTab){ selected ->
                        selectedTab = selected
                    }
                    if(selectedTab == 0){
                        if(recipeDetail?.materials?.isNotEmpty() == true){
                            MaterialListScreen(recipeDetail?.materials ?: listOf())
                        }
                    }
                    else{
                        if(recipeDetail?.steps?.isNotEmpty() == true){
                            StepListScreen(recipeDetail?.steps ?: listOf())
                        }
                    }

                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    BottomProfileView(ppUrl = recipe?.creatorPhotoURL ?: "", username = recipe?.creatorUserName ?: "")

                }
            }



        }
    }

}
@Composable
private fun BottomProfileView(ppUrl : String, username : String){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "Oluşturan",
            color = Color(0xff0a2533),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold),
            modifier = Modifier
                .wrapContentHeight(align = Alignment.CenterVertically))
    }
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        CircularImageView(url = ppUrl, size = 48)
        Spacer(modifier = Modifier.width(12.dp))
        Column() {
            Text(
                text = username,
                color = BrandPrimary,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium))
        }
    }
}

@Preview
@Composable
fun RecipeScreenPreview(){
    RecipeScreen(recipeId = "",
        navController = rememberNavController(),
        viewModel = viewModel()
    )
}