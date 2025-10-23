package com.example.dotillos.ui.screen.patient

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dotillos.ui.theme.AccentGray
import com.example.dotillos.ui.theme.BackgroundWhite
import com.example.dotillos.ui.theme.PrimaryBlue

@Composable
fun MyAppointmentScreen(modifier: Modifier = Modifier) {
    Column(modifier
        .fillMaxSize()
        .background(BackgroundWhite)
        .padding(16.dp)
    ) {
        Text(
            text = "Welcome to Dotillos Dental Clinic!",
            color = PrimaryBlue,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Appointment Card
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Next Appointment",
                    fontWeight = FontWeight.Medium,
                    color = AccentGray,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Dr. Smith - 25 Oct 2025, 10:00 AM",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = PrimaryBlue
                )
            }
        }


        // Announcement Card
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Announcements",
                    fontWeight = FontWeight.Medium,
                    color = AccentGray,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Flu season is here! Make sure to schedule your preventive check-ups.",
                    fontSize = 16.sp
                )
            }
        }
    }
}