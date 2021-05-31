package com.andreimargaritoiu.elearning.model.models

data class Workshop(
    val id: String = "",
    val description: String = "",
    val tag: String = "",
    val location: String = "",
    val thumbnailUrl: String = "",
    val date: Long = 0,
    val onlineEvent: Boolean = false,
    val participants: List<String> = emptyList(),
    val capacity: Long = 0,
)
