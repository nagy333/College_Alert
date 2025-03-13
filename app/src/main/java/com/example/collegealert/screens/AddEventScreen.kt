package com.example.collegealert.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.collegealert.R
import com.example.collegealert.Screens
import com.example.collegealert.composables.AppBarTitle
import com.example.collegealert.composables.AreUSureDialog
import com.example.collegealert.composables.LabelText
import com.example.collegealert.composables.MyDatePicker
import com.example.collegealert.composables.MyEditText
import com.example.collegealert.composables.MyFab
import com.example.collegealert.composables.MyTimePicker
import com.example.collegealert.ui_state.AddEventScreenUiState
import com.example.collegealert.viewmodels.AddEventScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    viewModel: AddEventScreenViewModel = hiltViewModel(),
    navController: NavController,
    data: Int,
) {
    LaunchedEffect(data) {
        viewModel.getEvent(id = data)
    }
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    AppBarTitle(title = state.barTitle)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),

                actions = {
                    if (data != -1) {
                        IconButton(onClick = {viewModel.onDeleteClicked() }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                })
        }, floatingActionButton = {
            MyFab(
                onClick = {
                    viewModel.onDoneCLicked()
                    if (state.title.isEmpty()||state.date.isEmpty()||state.time.isEmpty()) {
                        scope.launch {
                            snackbarHostState.showSnackbar(state.snackBarMsg)
                        }
                    }
                },
                navigate = {
                    if (state.title.isNotEmpty() && state.date.isNotEmpty() && state.time.isNotEmpty()) {
                        navController.navigate(Screens.EventsScreen.route){
                            popUpTo(Screens.EventsScreen.route){
                                inclusive = true
                            }
                        }
                    }
                },
                icon = Icons.Default.Check
            )
        }

    ) { padding ->
        AddEventScreenContent(
            padding = padding,
            state = state,
            onTitleChange = viewModel::onTitleChange,
            onSelectedDate = viewModel::onSelectDate,
            onCardClicked = viewModel::onDateCardClicked,
            onDismiss = viewModel::onDateDissmis,
            onTimeDismiss = viewModel::onTimeDismiss,
            onSelectedTime = viewModel::onSelectTime,
            onTimeCardClicked = viewModel::onTimeCardClicked,
            onDeleteDialogDismiss = viewModel::onDeleteDialogDismiss,
            onDeleteClicked = viewModel::onDialogDeleteClicked,
            navController = navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreenContent(
    padding: PaddingValues,
    state: AddEventScreenUiState,
    onTitleChange: (String) -> Unit,
    onSelectedDate: (Long) -> Unit,
    onCardClicked: () -> Unit,
    onDismiss: () -> Unit,
    onSelectedTime: (TimePickerState) -> Unit,
    onTimeCardClicked: () -> Unit,
    onTimeDismiss: () -> Unit,
    onDeleteDialogDismiss:()->Unit,
    onDeleteClicked:()->Unit,
    navController: NavController,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        val (tvTitle, etTitle,
            tvDate, datePick, timePick)= createRefs()

        LabelText(
            text = stringResource(R.string.event_label),
            modifier = Modifier.constrainAs(tvTitle) {
                top.linkTo(parent.top, margin = 30.dp)
                start.linkTo(parent.start, margin = 8.dp)
            })
        MyEditText(
            value = state.title,
            onValueChange = onTitleChange,
            placeHolder = stringResource(R.string.event_placeholder),
            modifier = Modifier.constrainAs(etTitle) {
                top.linkTo(tvTitle.bottom, margin = 4.dp)
                start.linkTo(parent.start, margin = 8.dp)
            }
        )
        LabelText(
            text = "Due Date",
            modifier = Modifier.constrainAs(tvDate) {
                top.linkTo(etTitle.bottom, margin = 16.dp)
                start.linkTo(parent.start, margin = 8.dp)
            }
        )
        MyDatePicker(
            showDatePicker = state.showDatePicker,
            onSelectDate = onSelectedDate,
            onDismiss = onDismiss,
            onCardClicked = onCardClicked,
            date = state.date,
            modifier = Modifier.constrainAs(datePick) {
                top.linkTo(tvDate.bottom, 8.dp)
                start.linkTo(parent.start, 8.dp)
            }
        )
        if (state.date.isNotEmpty()) {
            MyTimePicker(
                onCardClicked = onTimeCardClicked,
                onDismiss = onTimeDismiss,
                showTimePicker = state.showTimePicker,
                time = state.time,
                onTimeSelected = onSelectedTime,
                modifier = Modifier.constrainAs(timePick) {
                    top.linkTo(datePick.bottom, 8.dp)
                    start.linkTo(parent.start, 8.dp)
                }
            )
        }
        if (state.showDeleteDialog){
            AreUSureDialog(
                onDismiss = onDeleteDialogDismiss,
                onDeleteClicked = onDeleteClicked,
                navController = navController
            )
        }
    }
}
