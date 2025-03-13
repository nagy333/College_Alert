package com.example.collegealert.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyEditText(
    value: String,
    onValueChange:(String)->Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    placeHolder: String,
    color: Color = MaterialTheme.colorScheme.background,
    unfocusedIndicator: Color = Color.White,
    trailingIcon: @Composable() (()->Unit)? = null,
    leadingIcon: @Composable() (()->Unit)? = null,


){
    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(fontSize = 22.sp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = color,
            unfocusedContainerColor = color,
            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            cursorColor = Color.White,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = unfocusedIndicator,
            unfocusedTrailingIconColor = Color.White,
            focusedTrailingIconColor = Color.White,
            errorTextColor = Color.White,
            errorContainerColor =  MaterialTheme.colorScheme.background,

            ),
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        readOnly = readOnly,
        placeholder = {
            Text(
                text = placeHolder,
                fontSize = 20.sp,
                color = Color.LightGray)
        },
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(8.dp)

    )
}