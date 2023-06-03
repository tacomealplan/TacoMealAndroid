package com.android.taco.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.taco.R
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.main.views.home.HomeScreen
import com.android.taco.ui.main.views.cart.CartScreen
import com.android.taco.ui.main.views.chef.ChefScreen
import com.android.taco.ui.main.views.profile.ProfileScreen
import com.android.taco.ui.main.views.search.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel : MainViewModel by viewModels()
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
                SearchScreen()
            }
            composable(BottomNavItem.Chef.screen_route) {
                ChefScreen()
            }
            composable(BottomNavItem.Cart.screen_route) {
                CartScreen()
            }
            composable(BottomNavItem.Profile.screen_route) {
                ProfileScreen()
            }
        }
    }

    @Composable
    fun BottomNavigation(navController: NavController) {
        val items = listOf(
            BottomNavItem.Search,
            BottomNavItem.Chef,
            BottomNavItem.Home,
            BottomNavItem.Cart,
            BottomNavItem.Profile
        )
        BottomNavigation(
            backgroundColor = Color(0xFFFF8C00),
            contentColor = Color.White,
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)).height(height = 80.dp)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                    label = { /*Text(text = item.title,fontSize = 9.sp)*/ },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.White.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.screen_route,
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
                    }
                )
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

    object Home : BottomNavItem("Home", R.drawable.cart,"home")
    object Search: BottomNavItem("Search",R.drawable.search,"search")
    object Chef: BottomNavItem("Chef",R.drawable.chef,"chef")
    object Cart: BottomNavItem("Cart",R.drawable.cart,"cart")
    object Profile: BottomNavItem("Profile",R.drawable.profile,"profile")
}


