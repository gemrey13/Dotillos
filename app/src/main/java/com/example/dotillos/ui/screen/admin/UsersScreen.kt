package com.example.dotillos.ui.screen.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dotillos.ui.theme.PrimaryBlue


@Composable
fun UsersScreen(modifier: Modifier = Modifier) {
    Column {
        modifier.fillMaxSize().padding(24.dp)
    }
    Text(
        "Hi",
        color = PrimaryBlue,
        fontSize = 28.sp,
        textAlign = TextAlign.Center
    )
}