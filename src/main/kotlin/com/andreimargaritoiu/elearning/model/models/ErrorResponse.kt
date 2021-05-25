package com.andreimargaritoiu.elearning.model.models

data class ErrorResponse (
    private val status: String? = null,
    private val message: String? = null,
    private val path: String? = null
)