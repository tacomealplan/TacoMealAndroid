package com.android.taco.ui.main.views.chef.plan

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.android.taco.R
import com.android.taco.model.Plan
import com.android.taco.ui.main.views.chef.recipe.getUrlForStorage
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.components.buttons.CheckButton
import com.android.taco.ui.theme.components.buttons.RightArrowButton
import com.android.taco.ui.theme.components.image.CircularImageView

@Composable
fun PlanItem(plan: Plan, isActive : Boolean = false, onClick : () -> Unit){
    var creatorPpUrl by remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit){
        getUrlForStorage(plan.creatorProfileURL ?: ""){
            creatorPpUrl = it
        }
    }

    Surface(
        elevation = 9.dp, // play with the elevation values
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .clickable {
                onClick.invoke()
            }
    ){
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(70.dp)
                .padding(4.dp)
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Column(verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.8f)) {
                Text(
                    text = plan.name,
                    color = BrandPrimary,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold),
                    modifier = Modifier
                )
                Text(
                    text = plan.motivation,
                    color = BrandPrimary.copy(alpha = 0.9f),
                    style = TextStyle(
                        fontSize = 10.sp)
                )
                Text(
                    text = "2023/05/23 23:06",
                    color = BrandPrimary,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold),
                    modifier = Modifier
                )
            }
            Column(horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(end = 12.dp)
            ) {
                if(isActive){
                    CheckButton(size = 36) {
                        onClick.invoke()
                    }
                }else{
                    RightArrowButton(size = 36) {
                        onClick.invoke()
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun PlanItemPreview(){
    PlanItem(plan = Plan.dummyInstance(), isActive = false){}
}