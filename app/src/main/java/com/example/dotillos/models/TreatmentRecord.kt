package com.example.dotillos.models

import java.time.LocalDateTime

data class TreatmentRecord(
    val id: Int,
    val patientId: String,
    val dentistId: String,
    val serviceId: Int,
    val notes: String,
    val diagnosis: String,
    val createdAt: LocalDateTime
)
