package com.example.diavantagemobile.util.data.interfaces

import com.example.diavantagemobile.util.api.blood.BloodRepository
import com.example.diavantagemobile.util.api.glucose.GlucoseRepository
import com.example.diavantagemobile.util.api.login.CheckPatientRepository
import com.example.diavantagemobile.util.api.login.LoginRepository
import com.example.diavantagemobile.util.api.login.LogoutRepository

interface RemoteRepository {
    fun loginRepository(): LoginRepository
    fun checkPatientRepository(): CheckPatientRepository
    fun logoutRepository(): LogoutRepository
    fun glucoseRepository(): GlucoseRepository
    fun bloodRepository(): BloodRepository
}