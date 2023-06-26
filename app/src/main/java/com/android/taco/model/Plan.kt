package com.android.taco.model

import com.android.taco.ui.main.views.chef.plan.Day
import com.google.gson.Gson

data class Plan(
    val id: String,
    val creatorProfileURL: String,
    val creatorUserName: String,
    val motivation: String,
    val name: String,
    val mon : PlanDay,
    val tue : PlanDay,
    val wed : PlanDay,
    val thurs : PlanDay,
    val fri : PlanDay,
    val satur : PlanDay,
    val sun : PlanDay,
    val updateDate: String,
){
    fun getDay(day : Day) : PlanDay{
        return when(day){
            Day.Monday -> this.mon
            Day.Tuesday -> this.tue
            Day.Wednesday -> this.wed
            Day.Thursday -> this.thurs
            Day.Friday -> this.fri
            Day.Saturday -> this.satur
            Day.Sunday -> this.sun
        }
    }

    companion object{

        fun newInstance(data: MutableMap<String, Any>): Plan{
            val plan = Plan(
                id = data["Id"].toString(),
                creatorProfileURL = data["CreatorProfileURL"].toString(),
                creatorUserName = data["CreatorUsername"].toString(),
                motivation = data["Motivation"].toString(),
                name = data["Name"].toString(),
                mon = Gson().fromJson(data["Mon"].toString(), PlanDay::class.java),
                tue = Gson().fromJson(data["Tue"].toString(), PlanDay::class.java),
                wed = Gson().fromJson(data["Wed"].toString(), PlanDay::class.java),
                thurs = Gson().fromJson(data["Thurs"].toString(), PlanDay::class.java),
                fri = Gson().fromJson(data["Fri"].toString(), PlanDay::class.java),
                satur = Gson().fromJson(data["Satur"].toString(), PlanDay::class.java),
                sun = Gson().fromJson(data["Sun"].toString(), PlanDay::class.java),
                updateDate = data["UpdateDate"].toString()
            )
            return plan
        }

        fun dummyInstance(): Plan{
            val plan = Plan(
                id = "12345678",
                creatorProfileURL = "",
                creatorUserName = "Ahmet",
                motivation = "Motivasyon",
                name = "İsim",
                mon = PlanDay(breakfast = listOf("",""), lunch = listOf("",""), dinner = listOf("","")),
                tue = PlanDay(breakfast = listOf("",""), lunch = listOf("",""), dinner = listOf("","")),
                wed = PlanDay(breakfast = listOf("",""), lunch = listOf("",""), dinner = listOf("","")),
                thurs = PlanDay(breakfast = listOf("",""), lunch = listOf("",""), dinner = listOf("","")),
                fri = PlanDay(breakfast = listOf("",""), lunch = listOf("",""), dinner = listOf("","")),
                satur = PlanDay(breakfast = listOf("",""), lunch = listOf("",""), dinner = listOf("","")),
                sun = PlanDay(breakfast = listOf("",""), lunch = listOf("",""), dinner = listOf("","")),
                updateDate = "2023/04/27 23:57"
            )
            return plan
        }
    }
}
