package com.andreimargaritoiu.elearning.model.models

data class Inquiry(
    val id: String = "",
    val mentorId: String = "",
    val inquirerEmail: String = "",
    val read: Boolean = false,
    val createdAt: Long = 0
)
