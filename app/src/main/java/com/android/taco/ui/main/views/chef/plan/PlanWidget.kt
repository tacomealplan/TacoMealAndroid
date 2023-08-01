package com.android.taco.ui.main.views.chef.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.taco.ui.main.ScreensNavItem
import com.android.taco.ui.theme.BrandPrimary
import com.android.taco.ui.theme.BrandSecondary
import com.android.taco.ui.theme.NeutralGray3
import java.util.Calendar
import java.util.Date

@Composable
fun PlanWidget(navController: NavController, activePlanId : String = ""){
    Column(modifier = Modifier
        .fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Haftalık Menü",
                color = BrandPrimary,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold),
                modifier = Modifier
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth()
        ){
            DayList(selectedDay = Day.getCurrentDay()){
                navController.navigate(ScreensNavItem.Plan.screen_route + "/${activePlanId}")
            }
        }

    }
}

@Composable
fun DayList(selectedDay : Day = Day.Monday, onItemSelected : (day : Day) -> Unit) {
    Row(modifier = Modifier
    ) {
        Day.values().forEach { day ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clip(shape = RoundedCornerShape(40.dp))
                    .background(color = if (selectedDay == day) BrandSecondary else NeutralGray3)
                    .clickable {
                        onItemSelected.invoke(day)
                    }
            ) {
                Text(
                    text = day.name,
                    color = if(selectedDay == day) Color.White else BrandPrimary,
                    modifier = Modifier.padding(8.dp),
                    style = TextStyle(
                        fontSize = 16.sp))
            }
        }
    }
}

sealed class Day(var name:String){
    object Sunday : Day("Pazar")
    object Monday : Day("Pazartesi")
    object Tuesday : Day("Salı")
    object Wednesday : Day("Çarşamba")
    object Thursday : Day("Perşembe")
    object Friday : Day("Cuma")
    object Saturday : Day("Cumartesi")
    companion object {
        fun values() : ArrayList<Day> {
            val result = ArrayList<Day>()
            result.add(Monday)
            result.add(Tuesday)
            result.add(Wednesday)
            result.add(Thursday)
            result.add(Friday)
            result.add(Saturday)
            result.add(Sunday)
            return result

        }

        fun getCurrentDay() :Day{
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = Date()
            val day: Int = calendar.get(Calendar.DAY_OF_WEEK)

            when (day) {
                Calendar.SUNDAY -> {
                    return Sunday
                }
                Calendar.MONDAY -> {
                    return Monday
                }
                Calendar.TUESDAY -> {
                    return Tuesday
                }
                Calendar.WEDNESDAY -> {
                    return Wednesday
                }
                Calendar.THURSDAY -> {
                    return Thursday
                }
                Calendar.FRIDAY -> {
                    return Friday
                }
                Calendar.SATURDAY -> {
                    return Saturday
                }
            }
            return Monday
        }
    }
}

@Preview
@Composable
fun PlanWidgetPreview(){
    PlanWidget(navController = rememberNavController())
}