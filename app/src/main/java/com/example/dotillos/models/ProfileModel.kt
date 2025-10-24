package com.example.dotillos.models

import kotlinx.serialization.Serializable


@Serializable
data class ProfileModel(
    val id: String?,
    val name: String,
    val role: String, // Patient, Admin, Dentist, FrontDesk
    val phone: String?,
)
