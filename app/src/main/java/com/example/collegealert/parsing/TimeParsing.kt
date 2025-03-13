package com.example.collegealert.parsing

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
@OptIn(ExperimentalMaterial3Api::class)
fun convertStateToTime(state: TimePickerState):String{
    val hour = if(state.hour==0)12 else if(state.hour>12)state.hour-12 else state.hour
    val amPm = if(state.hour>12)"PM" else "AM"
   return String.format("%02d:%02d %s",hour,state.minute,amPm)
}

fun convertToDate(milli: Long): String{
    val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
    return formatter.format(Date(milli))
}
fun convertDateToMillis(date: String,hour: Int,minute: Int): Long{
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val d = dateFormat.parse(date)
    val cal = Calendar.getInstance().apply {
        d?.let { time = it }
        set(Calendar.HOUR_OF_DAY,hour)
        set(Calendar.MINUTE,minute)
        set(Calendar.SECOND,0)
    }
    return cal.timeInMillis
}