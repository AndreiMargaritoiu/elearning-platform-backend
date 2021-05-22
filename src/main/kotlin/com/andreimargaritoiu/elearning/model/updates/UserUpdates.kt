package com.andreimargaritoiu.elearning.model.updates

data class UserUpdates (
    val photoUrl: String = "",
    val following: List<String> = emptyList()
)