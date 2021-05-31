package com.andreimargaritoiu.elearning.model.builders

data class WorkshopBuilder(
    val description: String = "",
    val tag: String = "",
    val location: String = "",
    val thumbnailUrl: String = "",
    val date: Long = 0,
    val onlineEvent: Boolean = false,
    val capacity: Long = 0,
)
