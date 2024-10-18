package com.example.diavantagemobile.util.data.interfaces

import com.example.diavantagemobile.util.data.responses.LogoutResponse

interface LogoutRepository {
    suspend fun logout(): Boolean
}