package com.andreimargaritoiu.elearning.model

data class User (
        val username: String = "",
        val email: String = "",
        val profilePictureUrl: String = "",
        val videoRefs: List<String> = emptyList(),
        val searchIndex: List<String> = emptyList()
)