package com.example.diavantagemobile.util

data class LoginState(
    val username: String? = null,
    val isLogged: Boolean = false,
    val authToken: String? = null,
)
