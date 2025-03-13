package com.example.collegealert.repos

import com.example.collegealert.Db.EventEntity
import com.example.collegealert.Db.EventsDAO

class EventsRepo (private val dao: EventsDAO){

    suspend fun addEvent(event: EventEntity){
        dao.addEvent(event)
    }
    fun getEvents() = dao.getEvents()

   suspend fun getEventById(id: Int) = dao.getEventById(id)

    suspend fun updateEvent(event: EventEntity) = dao.updateEvent(event)

    suspend fun delete(id: Int) = dao.delete(id)

     fun getSearchedEvents(text: String) = dao.getSearchedEvents(text)
}