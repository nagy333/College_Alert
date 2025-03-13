package com.example.collegealert.Db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EventsDAO {
    @Insert
   suspend fun addEvent(event: EventEntity)

   @Query("select * from Events")
    fun getEvents(): Flow<List<EventEntity>>

    @Query("Select * from Events where id =:id")
   suspend fun getEventById(id: Int) :EventEntity

   @Update
   suspend fun updateEvent(event: EventEntity)

    @Query("delete from events where id =:id")
   suspend fun delete(id: Int)

   @Query("select * from Events where eventTitle like '%' || :text || '%'")
    fun getSearchedEvents(text: String): Flow<List<EventEntity>>
}