package com.andreimargaritoiu.elearning.model

data class Video(
        val id: String = "",
        val uid: String = "",
        val videoUrl: String = "",
        val thumbnailUrl: String = "",
        val title: String = "",
        val description: String = "",
        val createdAt: Long = 0
)