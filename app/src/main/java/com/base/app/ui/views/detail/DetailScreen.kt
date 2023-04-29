package com.base.app.ui.views.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.base.app.ui.theme.White

@Composable
fun StoryDetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    id: String,
    navController: NavController
) {
    Surface(
        color = White,
        modifier = Modifier.fillMaxSize()
    ) {
        LaunchedEffect(key1 = Unit, block = {

        })
        val isLoading by remember {
            viewModel.isLoading
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Column(modifier = Modifier.fillMaxHeight(0.5f)) {
                if(isLoading){
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }

                }else{
                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize(0.5f)) {
                        Text(text = "Detail Screen")

                    }
                }
            }

            Button(onClick = { viewModel.changeLoadingState() }) {
                Text(text = "Change Loading State")
            }
        }



    }
}
