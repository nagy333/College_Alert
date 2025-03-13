package com.example.collegealert.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.collegealert.R
import com.example.collegealert.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AreUSureDialog(
    onDismiss: () -> Unit,
    onDeleteClicked:()->Unit,
    navController: NavController,
) {

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.White)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (sure, cancel, del) = createRefs()
            MyText(
                text = stringResource(R.string.warning),
                modifier = Modifier.constrainAs(sure) {
                    top.linkTo(parent.top, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                })
            MyTextButton(
                onClick = {
                    onDeleteClicked()
                    navController.navigate(Screens.EventsScreen.route)
                },
                text = stringResource(R.string.delete),
                modifier = Modifier.constrainAs(del){
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                }
            )
            MyTextButton(
                onClick = {
                    onDismiss()
                },
                text = stringResource(R.string.cancel),
                modifier = Modifier.constrainAs(cancel){
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    end.linkTo(del.start,16.dp)
                }
            )

        }

    }
}

