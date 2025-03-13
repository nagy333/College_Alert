package com.example.collegealert.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.collegealert.R
import com.example.collegealert.Screens
import com.example.collegealert.composables.MyEditText
import com.example.collegealert.composables.MyFab
import com.example.collegealert.data.Event
import com.example.collegealert.ui_state.EventsScreenUiState
import com.example.collegealert.viewmodels.EventsScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    navController: NavController,
    viewModel: EventsScreenViewModel = hiltViewModel(),

    ) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (!state.isSearchClicked) {
                        Text(
                            text = stringResource(R.string.events_screen_title),
                            fontSize = 25.sp,
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults
                    .topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                actions = {
                    if (!state.isSearchClicked) {
                        IconButton(onClick = viewModel::onSearchClicked) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(30.dp)
                            )
                        }
                    } else {
                        MyEditText(
                            value = state.search,
                            onValueChange = viewModel::onSearchChange,
                            readOnly = false,
                            placeHolder = "Search",
                            color = MaterialTheme.colorScheme.primary,
                            unfocusedIndicator = MaterialTheme.colorScheme.primary,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            },
                            trailingIcon = {
                                if (state.search.isNotEmpty()) {
                                    IconButton(onClick = viewModel::onClearClicked) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = "clear",
                                            tint = Color.White,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                }

                            },
                            modifier = Modifier
                                .width(350.dp)
                        )
                    }
                },
                navigationIcon = {
                    if (state.isSearchClicked) {
                        IconButton(onClick = {
                            navController.navigate(Screens.EventsScreen.route)
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }
                }
            )

        },
        floatingActionButton = {
            MyFab(
                onClick = { navController.navigate(Screens.AddEventScreen.createRoute(-1)) },
                icon = Icons.Filled.Add,
                navigate = {}
            )


        }
    ) { padding ->
        EventsScreenContent(
            padding = padding,
            state = state,
            onCheckBoxClicked = viewModel::onCheckBoxClicked,
            navController = navController
        )
    }
}

@Composable
fun EventsScreenContent(
    padding: PaddingValues,
    state: EventsScreenUiState,
    onCheckBoxClicked: (Boolean, Event) -> Unit,
    navController: NavController
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)

    ) {
        val notFound = createRef()
        if (state.eventsList.isEmpty()&& state.search.isNotEmpty()){
            Text(
                text = "${state.search} not found",
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier.constrainAs(notFound){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.eventsList) {
                AnimatedVisibility(
                    visible = it.isVisible,
                    exit = slideOutHorizontally(animationSpec = tween(durationMillis = 1500)) {
                        it }
                ) {
                    EventItem(
                        onCheckBoxClicked = onCheckBoxClicked,
                        event = it,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun EventItem(
    event: Event,
    onCheckBoxClicked: (Boolean, Event) -> Unit,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
            .clickable { navController.navigate(Screens.AddEventScreen.createRoute(event.id)) },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (box, cTitle, cDate) = createRefs()
            Checkbox(
                checked = event.isChecked,
                onCheckedChange = { onCheckBoxClicked(true, event) },
                colors = CheckboxDefaults
                    .colors(
                        checkedColor = MaterialTheme.colorScheme.background,
                        uncheckedColor = Color.White,
                        checkmarkColor = Color.White,
                    ),
                modifier = Modifier.constrainAs(box) {
                    start.linkTo(parent.start, margin = 4.dp)
                    top.linkTo(parent.top, margin = 4.dp)
                }
            )
            Text(
                text = event.title,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier.constrainAs(cTitle) {
                    top.linkTo(parent.top, margin = 4.dp)
                    start.linkTo(box.end, margin = 4.dp)
                })
            Text(
                text = event.time,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.constrainAs(cDate) {
                    top.linkTo(cTitle.bottom, margin = 4.dp)
                    start.linkTo(cTitle.start)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                }
            )
        }
    }
}
