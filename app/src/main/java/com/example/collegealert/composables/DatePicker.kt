package com.example.collegealert.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.collegealert.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(
    modifier: Modifier = Modifier,
    onCardClicked: () -> Unit,
    showDatePicker: Boolean,
    onDismiss: () -> Unit,
    onSelectDate: (Long) -> Unit,
    date: String,
) {
    val datePickerState = rememberDatePickerState()
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (etDate, icon) = createRefs()
        MyEditText(
            value = date,
            onValueChange = {},
            readOnly = true,
            placeHolder = stringResource(R.string.date_placeholder),
            trailingIcon = {
                IconButton(
                    onClick = onCardClicked
                ) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Select Date",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCardClicked() }
                .constrainAs(etDate) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start,8.dp)
                    end.linkTo(parent.end,8.dp)
                }
        )
    }
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = onDismiss,
            colors = DatePickerDefaults
                .colors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = Color.White,
                    dividerColor = Color.White,
                    selectedDayContainerColor = Color.White,
                    selectedDayContentColor = MaterialTheme.colorScheme.background,
                    dayInSelectionRangeContainerColor = Color.White,
                    dayInSelectionRangeContentColor = MaterialTheme.colorScheme.primary,
                ),
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { onSelectDate(it) }
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(
                        text = stringResource(R.string.ok),
                        color = MaterialTheme.colorScheme.background
                    )
                }

            }, dismissButton = {
                Button(onClick = {
                    onDismiss()
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
                    Text(
                        text = "Cancel",
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }) {
            DatePicker(state = datePickerState)
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTimePicker(
    modifier: Modifier = Modifier,
    onCardClicked: () -> Unit,
    showTimePicker: Boolean,
    time: String,
    onTimeSelected: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (etDate, icon) = createRefs()
        MyEditText(
            value = time,
            onValueChange = {},
            readOnly = true,
            placeHolder = stringResource(R.string.time_placeholder),
            trailingIcon = {

                IconButton(
                    onClick = onCardClicked
                ) {
                    Icon(
                        painter = painterResource(R.drawable.time),
                        contentDescription = "Select Time",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCardClicked() }
                .constrainAs(etDate) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                }

        )

    }
    if (showTimePicker) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )
        MyTimePickerDialog(
            onSelectedTime = onTimeSelected,
            onDismiss = onDismiss
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTimePickerDialog(
    onSelectedTime: (TimePickerState) -> Unit,
    onDismiss: () -> Unit

) {
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false
    )
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = onDismiss,
    ) {
        Card(
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 16.dp),
            border = BorderStroke(width = 4.dp, color = Color.Black),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            ConstraintLayout {
                val (timePicker, confirm, cancel) = createRefs()
                TimePicker(
                    state = timePickerState,
                    colors = TimePickerDefaults
                        .colors(
                            selectorColor = Color.White,
                            containerColor = MaterialTheme.colorScheme.background,
                            timeSelectorSelectedContainerColor = Color.White,
                            timeSelectorSelectedContentColor = MaterialTheme.colorScheme.background,
                        ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .constrainAs(timePicker) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )
                Button(
                    onClick = { onSelectedTime(timePickerState) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier.constrainAs(confirm) {
                        top.linkTo(timePicker.bottom, margin = 4.dp)
                        start.linkTo(parent.start, 50.dp)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.confirm),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults
                        .buttonColors(
                            containerColor = Color.White
                        ),
                    modifier = Modifier.constrainAs(cancel) {
                        top.linkTo(timePicker.bottom, margin = 4.dp)
                        end.linkTo(parent.end, 50.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

    }

}

