package com.example.diavantagemobile.util.api.responses

import kotlinx.serialization.Serializable

@Serializable
data class UserSerializer(
    val username: String,
    val password: String,
    val email: String,
    val first_name: String?,
    val last_name: String?,
)
