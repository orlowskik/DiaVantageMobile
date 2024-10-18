package com.example.diavantagemobile.util.data.responses

import kotlinx.serialization.Serializable


@Serializable
data class LoginResponse(
    val key: String? = null,
)

@Serializable
data class LogoutResponse(
    val detail: String,
)

@Serializable
data class CheckPatientResponse(
    val patient_id: String? = null,
)

@Serializable
data class CsrfResponse(
    val detail: String,
    val CSRFToken : String,
)