package com.example.dotillos.models

data class OdontogramDetail(
    val id: Int,
    val recordId: Int,
    val toothNumber: String,
    val procedure: String,
    val notes: String
)