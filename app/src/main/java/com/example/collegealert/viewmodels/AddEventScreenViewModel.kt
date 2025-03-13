package com.example.collegealert.viewmodels

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collegealert.Db.EventEntity
import com.example.collegealert.R
import com.example.collegealert.alarm.AlarmManager
import com.example.collegealert.parsing.convertDateToMillis
import com.example.collegealert.parsing.convertMillisToDate
import com.example.collegealert.parsing.convertStateToTime
import com.example.collegealert.repos.EventsRepo
import com.example.collegealert.ui_state.AddEventScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEventScreenViewModel @Inject constructor(
    private val eventsRepo: EventsRepo,
    private val alarmManager: AlarmManager,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val _state = MutableStateFlow(AddEventScreenUiState())
    val state: StateFlow<AddEventScreenUiState> get() = _state

    fun onTitleChange(value: String) {
        _state.update { it.copy(title = value) }
    }

    fun getEvent(id: Int) {
        if (id != -1) {
            _state.update { it.copy(id = id) }
            viewModelScope.launch {
                val event = eventsRepo.getEventById(id)
                _state.update {
                    it.copy(
                        title = event.eventTitle,
                        date = event.dayTime,
                        time = event.hourTime,
                        barTitle = context.getString(R.string.new_event_title)
                    )
                }
            }
        }
    }

    fun onDoneCLicked() {
        if (_state.value.title.isNotEmpty() && _state.value.date.isNotEmpty() && _state.value.time.isNotEmpty()) {
            if (_state.value.id == -1) {
                val event = EventEntity(
                    eventTitle = _state.value.title,
                    eventTime = convertDateToMillis(
                        date = _state.value.date,
                        hour = _state.value.hour,
                        minute = state.value.minute
                    ),
                    dayTime = _state.value.date,
                    hourTime = _state.value.time
                )
                viewModelScope.launch {
                    eventsRepo.addEvent(event)
                }
            } else {
                viewModelScope.launch {
                    eventsRepo.updateEvent(
                        EventEntity(
                            id = _state.value.id,
                            eventTitle = _state.value.title,
                            eventTime = convertDateToMillis(
                                date = _state.value.date,
                                hour = _state.value.hour,
                                minute = state.value.minute
                            ),
                            dayTime = _state.value.date,
                            hourTime = _state.value.time
                        )
                    )
                }
            }

            alarmManager.schedule(
                context = context,
                time = convertDateToMillis(
                    date = _state.value.date,
                    hour = _state.value.hour,
                    minute = state.value.minute
                ),
                id = _state.value.id,
                title = _state.value.title
            )
        } else if (_state.value.title.isEmpty()) {
            _state.update { it.copy(snackBarMsg = "Enter Event at First") }
        }
        else if (_state.value.date.isEmpty()){
            _state.update { it.copy(snackBarMsg = "Enter Date at First") }
        }
        else if (_state.value.time.isEmpty()){
            _state.update { it.copy(snackBarMsg = "Enter Time at First") }
        }


    }

    fun onDeleteClicked() {
        _state.update { it.copy(showDeleteDialog = true) }
    }

    fun onDeleteDialogDismiss() {
        _state.update { it.copy(showDeleteDialog = false) }

    }

    fun onDateCardClicked() {
        _state.update { it.copy(showDatePicker = true) }
    }

    fun onSelectDate(date: Long) {
        _state.update { it.copy(date = convertMillisToDate(date)) }
    }

    fun onDateDissmis() {
        _state.update { it.copy(showDatePicker = false) }
    }

    fun onTimeCardClicked() {
        _state.update { it.copy(showTimePicker = true) }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun onSelectTime(state: TimePickerState) {
        _state.update { it.copy(hour = state.hour, minute = state.minute) }
        val time = convertStateToTime(state)
        _state.update { it.copy(time = time) }
        _state.update { it.copy(showTimePicker = false) }

    }

    fun onTimeDismiss() {
        _state.update { it.copy(showTimePicker = false) }
    }

    fun onDialogDeleteClicked() {
        viewModelScope.launch {
            eventsRepo.delete(_state.value.id)
            _state.update { it.copy(showDeleteDialog = false) }
        }
    }
}