package com.andreimargaritoiu.elearning.model.models

data class ErrorResponse (
    private val status: String?,
    private val message: String?,
    private val path: String?
)