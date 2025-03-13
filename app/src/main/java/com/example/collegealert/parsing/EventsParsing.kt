package com.example.collegealert.parsing

import com.example.collegealert.Db.EventEntity
import com.example.collegealert.data.Event
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertEventEntityToEvent(list: List<EventEntity>): List<Event>{
    val events: ArrayList<Event> = ArrayList()
    list.forEach{
        val event = Event(
            id = it.id,
            title = it.eventTitle,
            time = convertToDate(it.eventTime)
        )
        events.add(event)
    }
    return events
}
