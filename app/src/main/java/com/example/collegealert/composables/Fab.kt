package com.example.collegealert.composables

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MyFab(
    onClick:()->Unit,
    navigate:()->Unit,
    icon: ImageVector
){
    FloatingActionButton(
        onClick = {
            onClick()
            navigate()},
        shape = CircleShape,
        containerColor = Color.White) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier.size(30.dp))
    }
}