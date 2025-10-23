package com.example.dotillos.models

data class Prescription(
    val id: Int,
    val recordId: Int,
    val dentistId: String,
    val patientId: String,
    val content: String,
    val status: String // 'Draft', 'Finalized'
)