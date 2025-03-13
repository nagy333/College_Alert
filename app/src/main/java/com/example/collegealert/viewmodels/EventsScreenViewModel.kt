package com.example.collegealert.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collegealert.data.Event
import com.example.collegealert.parsing.convertEventEntityToEvent
import com.example.collegealert.repos.EventsRepo
import com.example.collegealert.ui_state.EventsScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsScreenViewModel @Inject constructor(
    private val eventsRepo: EventsRepo
) : ViewModel() {
    private val _state = MutableStateFlow(EventsScreenUiState())
    val state: StateFlow<EventsScreenUiState> get() = _state

    init {
        getEvents()
    }

    private fun getEvents() {
        viewModelScope.launch {
            eventsRepo.getEvents().collect { it ->
                val events = convertEventEntityToEvent(it)
                _state.update { it.copy(eventsList = events) }
            }
        }

    }

    private fun getSearchedEvents() {
        if (_state.value.isSearchClicked) {
            viewModelScope.launch {
                _state
                    .map { it.search }
                    .collectLatest { text ->
                       val results = if (text.isNotEmpty())
                           eventsRepo.getSearchedEvents(text).first()
                       else eventsRepo.getEvents().first()
                        _state.update { it.copy(eventsList = convertEventEntityToEvent(results)) }
                    }
            }
        }
    }

    fun onCheckBoxClicked(checked: Boolean, event: Event) {
        val events = _state.value.eventsList.map {
            if (event.id == it.id) {
                viewModelScope.launch {
                    delay(1000)
                    eventsRepo.delete(event.id)
                }
                it.copy(isChecked = !it.isChecked, isVisible = false)

            } else it
        }
        _state.update { it.copy(eventsList = events) }
    }

    fun onSearchClicked() {
        _state.update { it.copy(isSearchClicked = true) }
        getSearchedEvents()
    }

    fun onSearchChange(value: String) {
        _state.update { it.copy(search = value) }
    }

    fun onClearClicked() {
        _state.update { it.copy(search = "") }
    }

}
