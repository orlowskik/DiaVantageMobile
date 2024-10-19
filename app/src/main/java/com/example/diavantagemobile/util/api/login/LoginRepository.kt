package com.example.diavantagemobile.util.api.login

import com.example.diavantagemobile.util.api.responses.LoginResponse

interface LoginRepository {
    suspend fun login(username: String, password: String): LoginResponse?
}