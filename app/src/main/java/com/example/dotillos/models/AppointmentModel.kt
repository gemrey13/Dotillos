package com.example.dotillos.models

import java.time.LocalDateTime

data class Appointment(
    val id: Int,
    val patientId: String,
    val dentistId: String,
    val serviceId: Int,
    val appointmentTime: LocalDateTime, // Using LocalDateTime for time
    val status: String, // 'Scheduled', 'Cancelled', 'Completed'
    val paymentStatus: String // 'Paid', 'Unpaid'
)