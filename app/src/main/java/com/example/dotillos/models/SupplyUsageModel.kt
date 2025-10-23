package com.example.dotillos.models

data class SupplyUsage(
    val id: Int,
    val recordId: Int,
    val supplyId: Int,
    val quantityUsed: Int
)
