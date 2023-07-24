package com.android.taco.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.taco.R
import com.android.taco.model.Plan
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.main.views.home.HomeScreen
import com.android.taco.ui.main.views.cart.CartScreen
import com.android.taco.ui.main.views.chef.ChefScreen
import com.android.taco.ui.main.views.chef.plan.PlanScreen
import com.android.taco.ui.main.views.chef.plan.edit.PlanEditScreen
import com.android.taco.ui.main.views.chef.plan.edit.PlanInfoEditScreen
import com.android.taco.ui.main.views.chef.recipe.detail.RecipeScreen
import com.android.taco.ui.main.views.chef.recipe.edit.EditRecipeDetailScreen
import com.android.taco.ui.main.views.chef.recipe.edit.EditRecipeScreen
import com.android.taco.ui.main.views.favourites.FavouritesScreen
import com.android.taco.ui.main.views.populars.PopularsScreen
import com.android.taco.ui.main.views.profile.ProfileEditScreen
import com.android.taco.ui.main.views.profile.ProfileScreen
import com.android.taco.ui.main.views.search.SearchScreen
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.BrandSecondary
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel : MainViewModel by viewModels()
    val items = listOf(
        BottomNavItem.Search,
        BottomNavItem.Chef,
        BottomNavItem.Home,
        BottomNavItem.Cart,
        BottomNavItem.Profile
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TacoTheme {
                MainScreenView()
            }
        }
    }

    @Composable
    fun MainScreenView(){
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigation(navController = navController) }
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(it)) {
                NavigationGraph(navController = navController)
            }

        }
    }

    @Composable
    fun NavigationGraph(navController: NavHostController) {
        NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
            composable(BottomNavItem.Home.screen_route) {
                HomeScreen(navController = navController)
            }
            composable(BottomNavItem.Search.screen_route) {
                SearchScreen(navController = navController, viewModel = viewModel())
            }
            composable(BottomNavItem.Chef.screen_route) {
                ChefScreen(navController = navController, viewModel = viewModel())
            }
            composable(BottomNavItem.Cart.screen_route) {
                CartScreen(viewModel = viewModel())
            }
            composable(BottomNavItem.Profile.screen_route) {
                ProfileScreen(navController = navController, viewModel = viewModel())
            }
            composable(BottomNavItem.Profile.screen_route +"/Edit") {
                ProfileEditScreen(navController = navController, viewModel = viewModel())
            }
            composable(ScreensNavItem.Favourites.screen_route) {
                FavouritesScreen(navController = navController, viewModel = viewModel())
            }
            composable(ScreensNavItem.Populars.screen_route) {
                PopularsScreen(navController = navController, viewModel = viewModel())
            }

            composable(ScreensNavItem.Plan.screen_route+ "/{planId}") {
                it.arguments?.getString("planId")?.let { planId ->
                    PlanScreen(planId = planId, viewModel= viewModel(), navController = navController)
                }
            }

            composable(ScreensNavItem.EditPlan.screen_route + "/{planId}") {
                it.arguments?.getString("planId")?.let { planId ->
                    PlanEditScreen(planId = planId, viewModel= viewModel(), navController = navController)
                }
            }
            composable(ScreensNavItem.EditPlan.screen_route) {
                PlanEditScreen(planId = "", viewModel= viewModel(), navController = navController)
            }
            composable(ScreensNavItem.EditPlanInfo.screen_route) {
                PlanInfoEditScreen(planId = "", viewModel= viewModel(), navController = navController)
            }

            composable(ScreensNavItem.Recipe.screen_route + "/{recipeId}") {
                it.arguments?.getString("recipeId")?.let { recipeId ->
                    RecipeScreen(recipeId = recipeId, navController = navController, viewModel = viewModel())
                }
            }
            composable(ScreensNavItem.EditRecipe.screen_route + "/{recipeId}") {
                it.arguments?.getString("recipeId")?.let { recipeId ->
                    EditRecipeScreen(recipeId = recipeId, navController = navController, viewModel = viewModel())
                }
            }
            composable(ScreensNavItem.EditRecipe.screen_route) {
                EditRecipeScreen(recipeId = "", navController = navController, viewModel = viewModel())
            }

            composable(ScreensNavItem.EditRecipeDetail.screen_route) {
                EditRecipeDetailScreen(navController = navController, viewModel = viewModel())
            }
        }
    }

    @Composable
    fun BottomNavigation(navController: NavController) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        if(items.any { it.screen_route == currentRoute }){
            BottomNavigation(
                backgroundColor = BrandSecondary,
                contentColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp))
                    .height(height = 80.dp)
            ) {
                items.forEach { item ->
                    val isSelected = currentRoute?.contains(item.screen_route) == true
                    BottomNavigationItem(
                        icon = { Icon(painterResource(id = item.icon),
                            contentDescription = item.title,
                            modifier = Modifier.size(if(isSelected) 36.dp else 24.dp))
                        },
                        label = { /*Text(text = item.title,fontSize = 9.sp)*/ },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.White.copy(0.8f),
                        alwaysShowLabel = true,
                        selected = isSelected,
                        onClick = {
                            navController.navigate(item.screen_route) {

                                navController.graph.startDestinationRoute?.let { screen_route ->
                                    popUpTo(screen_route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        modifier = if (isSelected) {
                            Modifier.background(color = Color.Transparent)
                        } else {
                            Modifier.background(color = Color.Transparent)
                        }
                    )
                }
            }
        }

    }

    companion object{
        fun start(context : Context){
            context.startActivity(Intent(context,MainActivity::class.java))
        }
    }
}

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItem("Home", R.drawable.home,"home")
    object Search: BottomNavItem("Search",R.drawable.search,"search")
    object Chef: BottomNavItem("Chef",R.drawable.chef,"chef")
    object Cart: BottomNavItem("Cart",R.drawable.cart,"cart")
    object Profile: BottomNavItem("Profile",R.drawable.profile,"profile")
}

sealed class ScreensNavItem(var title:String, var screen_route:String){
    object Favourites : ScreensNavItem("Favourites", "favourites")
    object Populars : ScreensNavItem("Populars", "populars")
    object Plan : ScreensNavItem("Plan", "plan")
    object EditPlan : ScreensNavItem("EditPlan", "edit_plan")
    object EditPlanInfo : ScreensNavItem("EditPlanInfo", "edit_plan_info")
    object Recipe : ScreensNavItem("Recipe", "recipe")
    object EditRecipe : ScreensNavItem("EditRecipe", "edit_recipe")
    object EditRecipeDetail : ScreensNavItem("EditRecipeDetail", "edit_recipe_detail")
}


