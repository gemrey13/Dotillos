package com.example.dotillos.models

import java.time.LocalDateTime

data class Notification(
    val id: Int,
    val userId: String,
    val type: String, // 'Appointment', 'Prescription'
    val message: String,
    val sentAt: LocalDateTime,
    val read: Boolean
)