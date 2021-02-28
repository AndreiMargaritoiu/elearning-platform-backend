package com.andreimargaritoiu.elearning.model

data class Playlist (
        val userId: String = "",
        val name: String = "",
        val videos: List<String> = emptyList()
)