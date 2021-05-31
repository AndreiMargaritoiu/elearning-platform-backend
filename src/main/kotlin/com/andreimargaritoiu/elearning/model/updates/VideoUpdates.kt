package com.andreimargaritoiu.elearning.model.updates

data class VideoUpdates(
    val title: String = "",
    val description: String = "",
    val searchIndex: List<String> = emptyList()
)