package com.andreimargaritoiu.elearning.model.updates

data class PlaylistUpdates(
    val title: String = "",
    val description: String = "",
    val videoRefs: List<String> = emptyList(),
    val searchIndex: List<String> = emptyList()
)