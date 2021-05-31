package com.andreimargaritoiu.elearning.model.models

data class User (
        val uid: String = "",
        val username: String = "",
        val email: String = "",
        val photoUrl: String = "",
        val following: List<String> = emptyList(),
        val searchIndex: List<String> = emptyList(),
        val admin: Boolean = false,
)