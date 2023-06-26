package com.android.taco.ui.main.views.chef.plan

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.android.taco.ui.theme.components.buttons.RightArrowButton
import com.android.taco.ui.theme.components.image.CircularImageView

@Composable
fun PlanItem(plan: Plan, onClick : () -> Unit){
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
            .padding(4.dp)
            .clickable {
                onClick.invoke()
            }
    ){
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(100.dp)
                .padding(12.dp)
        ) {

            Column(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.8f)
                .padding(8.dp)) {
                Text(
                    text = plan.name,
                    color = BrandPrimary,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold),
                    modifier = Modifier
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularImageView(url = creatorPpUrl, size = 30)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = plan.creatorUserName,
                        color = Color(0xff97a2b0).copy(alpha = 0.75f),
                        style = TextStyle(
                            fontSize = 14.sp)
                    )
                }




            }
            Column(modifier = Modifier.fillMaxWidth(1f)) {
                RightArrowButton {

                }
            }

        }
    }
}

@Preview
@Composable
fun PlanItemPreview(){
    PlanItem(plan = Plan.dummyInstance()){}
}