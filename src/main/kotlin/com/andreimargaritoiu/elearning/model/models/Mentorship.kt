package com.andreimargaritoiu.elearning.model.models

data class Mentorship (
        val id: String = "",
        val description: String = "",
        val mentorId: String = "",
        val mentorEmail: String = "",
        val price: Long = 0,
        val createdAt: Long = 0
)
