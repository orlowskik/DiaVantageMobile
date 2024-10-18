package com.example.diavantagemobile.util.data.interfaces

import com.example.diavantagemobile.util.data.responses.LoginResponse

interface LoginRepository {
    suspend fun login(username: String, password: String): LoginResponse?
}