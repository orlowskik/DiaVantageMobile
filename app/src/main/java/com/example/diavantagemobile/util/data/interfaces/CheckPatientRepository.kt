package com.example.diavantagemobile.util.data.interfaces

import com.example.diavantagemobile.util.data.responses.CheckPatientResponse

interface CheckPatientRepository {
    suspend fun checkPatient(): CheckPatientResponse?
}