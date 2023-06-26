package com.android.taco.ui.main.views.chef.steps

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.taco.R
import com.android.taco.model.Material
import com.android.taco.model.Step
import com.android.taco.ui.main.views.chef.materials.MaterialRow
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.TacoTheme
import com.android.taco.ui.theme.White

@Composable
fun StepListScreen(stepList: ArrayList<Step>){
    TacoTheme() {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(color = Color.White)
        ) {


            Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Adımlar",
                    color = Color(0xff0a2533),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                )

                Text(
                    text = "${stepList.size} Adım",
                    color = Color(0xff748189),
                    style = TextStyle(
                        fontSize = 16.sp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
            ) {
                //stepList.forEach {
                  //  StepRow(it)
                //}

            }
        }
    }
}

@Composable
fun StepRow(step: Step) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = White,
        elevation = 9.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Number(step.index)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = step.description,
                color = Color(0xff48525f),
                style = TextStyle(
                    fontSize = 16.sp),
                modifier = Modifier
                    .fillMaxWidth())
        }
    }
}

@Composable
fun Number(number : Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size = 28.dp)
            .clip(shape = RoundedCornerShape(6.dp))
            .background(color = Color(0xffe6ebf2))){
        Text(
            text = "$number",
            color = Color(0xffff8c00),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold))
    }
}

@Preview
@Composable
fun StepListScreenPreview(){
    StepListScreen(arrayListOf<Step>(
        Step("","",1,""),
        Step("","",2,""),
        Step("","",3,""),
    ))
}