package com.example.collegealert.ui_state

import com.example.collegealert.data.Event

data class EventsScreenUiState(
    val eventsList: List<Event> = emptyList(),
    val checked: Boolean = false,
    var checkList: List<CheckedList> = emptyList(),
    var isSearchClicked: Boolean = false,
    val search: String = "",

    )
data class CheckedList(
    var index: Int = -1,
    var isChecked: Boolean = false
)
