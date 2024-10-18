package com.example.diavantagemobile.util

data class LoginState(
    val username: String? = null,
    val password : String? = null,
    val isLogged: Boolean = false,
    val patientId: String? = null,
)
