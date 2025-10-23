package com.example.dotillos.ui.screen.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dotillos.ui.theme.AccentGray
import com.example.dotillos.ui.theme.BackgroundWhite
import com.example.dotillos.ui.theme.PrimaryBlue


@Composable
@Preview(showBackground = true)
fun NotificationScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .background(BackgroundWhite)
            .padding(16.dp)
    ) {
        Text(
            text = "Notifications",
            color = PrimaryBlue,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        NotificationCard(
            title = "Appointment Reminder",
            message = "You have an appointment with Dr. Cruz tomorrow at 2:00 PM."
        )

        NotificationCard(
            title = "Promo Alert",
            message = "Enjoy 10% off on dental cleaning this month. Book now!"
        )

        NotificationCard(
            title = "New Service",
            message = "We now offer cosmetic teeth whitening. Ask your dentist for details."
        )

        NotificationCard(
            title = "System Update",
            message = "The clinic app has been updated with a faster booking system."
        )
    }
}


@Composable
fun NotificationCard(title: String, message: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                color = AccentGray,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                fontSize = 16.sp,
                color = PrimaryBlue
            )
        }
    }
}