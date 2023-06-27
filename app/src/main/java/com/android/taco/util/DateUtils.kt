package com.android.taco.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

//2023/04/29 20:58
fun now(): String {
    return SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault()).format(Date())
}

fun getDate(str : String) : Date?{
    val dateFormated = try {
        SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault()).parse(str)
    } catch (e: Exception) {
        return null
    }
    return dateFormated
}