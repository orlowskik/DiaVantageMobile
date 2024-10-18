package com.example.diavantagemobile.util.data.interfaces

interface RemoteRepository {
    fun loginRepository(): LoginRepository
    fun checkPatientRepository(): CheckPatientRepository
    fun logoutRepository(): LogoutRepository
}