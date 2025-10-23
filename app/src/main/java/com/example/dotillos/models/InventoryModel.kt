package com.example.dotillos.models

data class Inventory(
    val id: Int,
    val name: String,
    val description: String,
    val quantity: Int,
    val unit: String,
    val reorderLevel: Int
)