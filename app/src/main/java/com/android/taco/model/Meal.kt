package com.android.taco.model

import java.util.Calendar
import java.util.Date

sealed class Meal(var name:String){
    object Breakfast : Meal("Kahvaltı")
    object Lunch : Meal("Öğle Yemeği")
    object Dinner : Meal("Akşam Yemeği")
    companion object {
        fun values() : ArrayList<Meal> {
            val result = ArrayList<Meal>()
            result.add(Breakfast)
            result.add(Lunch)
            result.add(Dinner)
            return result
        }

    }
}