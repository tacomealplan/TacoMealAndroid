package com.android.taco.model

import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.Gson


data class Plan(
    var id: String,
    val creatorProfileURL: String,
    var creatorUserName: String,
    var createdBy: String,
    var motivation: String,
    var name: String,
    val mon : PlanDay,
    val tue : PlanDay,
    val wed : PlanDay,
    val thurs : PlanDay,
    val fri : PlanDay,
    val satur : PlanDay,
    val sun : PlanDay,
    val updateDate: String,
    var createDate: String,
    var categories: ArrayList<String>,
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

    fun addRecipeToPlan(recipe: Recipe, day: Day, meal: Meal){
        when(day){
            Day.Monday ->{
                this.mon.addRecipeToDailyPlan(meal, recipe)
            }
            Day.Tuesday ->{
                this.tue.addRecipeToDailyPlan(meal, recipe)
            }
            Day.Wednesday ->{
                this.wed.addRecipeToDailyPlan(meal, recipe)
            }
            Day.Thursday ->{
                this.thurs.addRecipeToDailyPlan(meal, recipe)
            }
            Day.Friday ->{
                this.fri.addRecipeToDailyPlan(meal, recipe)
            }
            Day.Saturday ->{
                this.satur.addRecipeToDailyPlan(meal, recipe)
            }
            Day.Sunday ->{
                this.sun.addRecipeToDailyPlan(meal, recipe)
            }
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
                updateDate = data["UpdateDate"].toString(),
                createdBy = data["CreatedBy"].toString(),
                createDate = data["CreateDate"].toString(),
                categories = data["Categories"] as ArrayList<String>,
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
                updateDate = "2023/04/27 23:57",
                createDate = "2023/04/27 23:57",
                createdBy = "1234567890",
                categories = ArrayList<String>(),
            )
            return plan
        }

        fun toHashMap(plan: Plan) : HashMap<String,Any>{
            val item = HashMap<String,Any>()
            item["Id"] = plan.id
            item["CreatorProfileURL"] = plan.creatorProfileURL
            item["CreatorUsername"] = plan.creatorUserName
            item["Motivation"] = plan.motivation
            item["Name"] = plan.name
            item["Mon"] = Gson().toJson(plan.mon)
            item["Tue"] = Gson().toJson(plan.tue)
            item["Wed"] = Gson().toJson(plan.wed)
            item["Thurs"] = Gson().toJson(plan.thurs)
            item["Fri"] = Gson().toJson(plan.fri)
            item["Satur"] = Gson().toJson(plan.satur)
            item["Sun"] = Gson().toJson(plan.sun)
            item["UpdateDate"] = plan.updateDate
            item["CreatedBy"] = plan.createdBy
            item["CreateDate"] = plan.createDate
            item["Categories"] = plan.categories

            return item
        }
    }
}
