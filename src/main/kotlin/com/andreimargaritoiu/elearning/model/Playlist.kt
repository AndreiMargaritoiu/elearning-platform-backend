package com.andreimargaritoiu.elearning.model

data class Playlist (
        val id: String = "",
        val uid: String = "",
        val title: String = "",
        val description: String = "",
        val category: String = "",
        val thumbnailUrl: String = "",
        val videoRefs: List<String> = emptyList(),
        val searchIndex: List<String> = emptyList(),
        val createdAt: Long = 0
)