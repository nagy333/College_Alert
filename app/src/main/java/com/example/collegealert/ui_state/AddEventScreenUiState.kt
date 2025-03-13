package com.example.collegealert.ui_state

data class AddEventScreenUiState(
    val id: Int = -1,
    val title: String = "",
    val date: String = "",
    val time: String = "",
    val hour: Int = 0,
    val minute: Int = 0,
    val timInMillis: Long = 0,
    val barTitle: String = "",
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val snackBarMsg: String = "",
)
