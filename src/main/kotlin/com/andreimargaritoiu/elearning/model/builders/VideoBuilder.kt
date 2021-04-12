package com.andreimargaritoiu.elearning.model.builders

data class VideoBuilder (
    val uid: String = "",
    val videoUrl: String = "",
    val thumbnailUrl: String = "",
    val title: String = "",
    val description: String = "",
    val searchIndex: List<String> = emptyList()
)