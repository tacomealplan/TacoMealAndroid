package com.tacomeal.taco.ui.theme.components.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tacomeal.taco.ui.theme.BrandPrimary
import com.tacomeal.taco.ui.theme.NeutralGray4

@Composable
fun TabsTwoOptions(tabs : ArrayList<String>, selectedTab : Int, onTabSelected : (tab : Int) -> Unit){
    Surface(shape = RoundedCornerShape(16.dp),
        color = NeutralGray4,
        modifier = Modifier.wrapContentSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .wrapContentSize()
                .padding(4.dp)
        ) {
            tabs.forEachIndexed { index, s ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(color = if (selectedTab == index) BrandPrimary else NeutralGray4)
                        .clickable {
                            onTabSelected.invoke(index)
                        }
                ) {
                    Text(
                        text = s,
                        textAlign = TextAlign.Center,
                        color = if(selectedTab == index) Color.White else BrandPrimary,
                        modifier = Modifier
                            .width(150.dp)
                            .padding(vertical = 16.dp),
                        style = TextStyle(
                            fontSize = 16.sp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TabsTwoOptionsPreview(){
    TabsTwoOptions(tabs = arrayListOf("Malzemeler", "Adımlar"),selectedTab = 0){}
}