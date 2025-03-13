package com.example.collegealert.Db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [EventEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EventsDB : RoomDatabase() {
    abstract fun dao(): EventsDAO

    companion object {
        @Volatile
        private var INSTANCE: EventsDB? = null
        fun getInstance(context: Context): EventsDB {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDB(context).also { INSTANCE = it }
            }
        }

        private fun buildDB(context: Context): EventsDB {
            return Room.databaseBuilder(context, EventsDB::class.java, name = "Events Db").build()
        }
    }

}