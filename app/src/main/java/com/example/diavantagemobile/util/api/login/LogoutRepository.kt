package com.example.diavantagemobile.util.api.login

interface LogoutRepository {
    suspend fun logout(): Boolean
}