package com.tacomeal.taco.model

import java.util.Calendar
import java.util.Date

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