package com.example.diavantagemobile.util.api.login

import com.example.diavantagemobile.util.api.responses.CheckPatientResponse

interface CheckPatientRepository {
    suspend fun checkPatient(): CheckPatientResponse?
}