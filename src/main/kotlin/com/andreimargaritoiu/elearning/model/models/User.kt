package com.andreimargaritoiu.elearning.model.models

data class User (
        val id: String = "",
        val username: String = "",
        val email: String = "",
        val profilePictureUrl: String = "",
        val following: List<String> = emptyList(),
        val searchIndex: List<String> = emptyList()
)