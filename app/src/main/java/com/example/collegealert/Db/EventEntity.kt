package com.example.collegealert.Db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val eventTitle: String,
    val eventTime: Long,
    val dayTime: String,
    val hourTime: String,
)
