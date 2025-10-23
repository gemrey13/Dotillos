package com.example.dotillos.models

data class Invoice(
    val id: Int,
    val appointmentId: Int,
    val patientId: String,
    val totalAmount: Double,
    val taxAmount: Double,
    val discount: Double,
    val finalAmount: Double,
    val status: String // 'Paid', 'Unpaid', 'Pending'
)