package com.example.collegealert.composables

import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MyTextButton(
    modifier: Modifier = Modifier,
    onClick:()->Unit,
    text: String,
){
    TextButton(
        onClick = onClick,
        modifier = modifier) {
        MyText(
            text = text
        )
    }
}