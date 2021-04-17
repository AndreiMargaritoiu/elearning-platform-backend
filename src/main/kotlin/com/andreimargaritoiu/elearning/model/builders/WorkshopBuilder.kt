package com.andreimargaritoiu.elearning.model.builders

data class WorkshopBuilder(
    val description: String = "",
    val tag: String = "",
    val location: String = "",
    val date: Long = 0,
    val isOnline: Boolean = false
)
