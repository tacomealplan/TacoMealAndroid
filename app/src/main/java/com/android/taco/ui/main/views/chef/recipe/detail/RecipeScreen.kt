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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
import com.android.taco.ui.main.views.chef.recipe.ButtonLike
import com.android.taco.ui.main.views.chef.steps.StepListScreen
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.BrandSecondary
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
                CircularProgressIndicator(color = BrandSecondary)
            }else{
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = rememberImagePainter(
                            data = coverPhotoUrl,
                            builder = {
                                crossfade(false)
                                placeholder(R.color.imagePlaceholderColor)
                            }
                        ),
                        contentDescription = "description",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                    )
                    Column(
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Transparent)
                    ) {
                        HeaderBar(navController)
                        Spacer(modifier = Modifier.height(120.dp))
                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .verticalScroll(state = rememberScrollState())
                                .fillMaxSize()
                                .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                        ){
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = recipe?.name ?: "",
                                color = BrandPrimary,
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 6.dp)
                                    .padding(horizontal = 24.dp)
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(
                                        color = BrandPrimary,
                                        fontSize = 16.sp)
                                    ) {append(recipe?.description ?: "")}
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp)
                                    .padding(bottom = 12.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp)
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
                                        color = BrandPrimary,
                                        style = TextStyle(
                                            fontSize = 16.sp))
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
                                        color = BrandPrimary,
                                        style = TextStyle(
                                            fontSize = 16.sp))
                                }
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(24.dp)
                            ) {
                                TabsTwoOptions(tabs = arrayListOf("Malzemeler", "Adımlar"), selectedTab){ selected ->
                                    selectedTab = selected
                                }
                                if(selectedTab == 0){
                                    if(recipeDetail?.materials?.isNotEmpty() == true){
                                        MaterialListScreen(recipeDetail?.materials ?: arrayListOf())
                                    }
                                }
                                else{
                                    if(recipeDetail?.steps?.isNotEmpty() == true){
                                        StepListScreen(recipeDetail?.steps ?: arrayListOf())
                                    }
                                }
                            }
                            Divider(modifier = Modifier.padding(horizontal = 24.dp))
                            BottomProfileView(ppUrl = recipe?.creatorPhotoURL ?: "", username = recipe?.creatorUserName ?: "")
                            Spacer(modifier = Modifier.height(12.dp))

                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun HeaderBar(navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 12.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.cancel),
            contentDescription = "Localized description",
            tint= Color.White,
            modifier = Modifier.clickable {
                navController.popBackStack()
            })
        ButtonLike(size = 48) {

        }
    }
}

@Composable
private fun BottomProfileView(ppUrl : String, username : String){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
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