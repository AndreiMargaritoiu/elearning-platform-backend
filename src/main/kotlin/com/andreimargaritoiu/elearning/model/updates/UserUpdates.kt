package com.andreimargaritoiu.elearning.model.updates

data class UserUpdates (
    val profilePictureUrl: String = "",
    val following: List<String> = emptyList()
)