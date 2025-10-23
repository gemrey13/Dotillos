package com.example.dotillos.models

data class RegisterResult(
    val success: Boolean,
    val requiresVerification: Boolean = false,
    val errorMessage: String? = null
)