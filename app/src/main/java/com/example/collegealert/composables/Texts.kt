package com.example.collegealert.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun AppBarTitle(
    title: String,
){
    Text(
        text = title,
        fontSize = 25.sp,
        color = Color.White)
}

@Composable
fun LabelText(
    text: String,
    modifier: Modifier = Modifier
){
    Text(
        text = text,
        fontSize = 22.sp,
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier)
}
@Composable
fun MyText(
    modifier: Modifier = Modifier,
    text: String,
){
    Text(
        text = text,
        fontSize = 25.sp,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier)
}
