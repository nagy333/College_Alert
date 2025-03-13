package com.example.collegealert.data

data class Event(
    val id: Int = 0,
    val title: String = "",
    val time: String = "",
    val isChecked: Boolean = false,
    val isVisible: Boolean = true,
)
