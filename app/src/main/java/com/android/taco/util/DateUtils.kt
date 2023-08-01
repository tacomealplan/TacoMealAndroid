package com.android.taco.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

//2023/04/29 20:58
fun now(): String {
    return SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault()).format(Date())
}

fun getCurrentWeekOfYear(): Int {
    val cal: Calendar = Calendar.getInstance(Locale("tr","TR"))
    val today = Date()
    cal.time = today
    return cal.get(Calendar.WEEK_OF_YEAR)-1
}

fun getDate(str : String) : Date?{
    val dateFormated = try {
        SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault()).parse(str)
    } catch (e: Exception) {
        return null
    }
    return dateFormated
}