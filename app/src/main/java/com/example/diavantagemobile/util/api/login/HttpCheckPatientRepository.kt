package com.example.diavantagemobile.util.api.login

import android.util.Log
import com.example.diavantagemobile.util.api.responses.CheckPatientResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class HttpCheckPatientRepository(
    private val client: HttpClient,
    private val endpoint: String,
) : CheckPatientRepository {
    override suspend fun checkPatient(): CheckPatientResponse? {
        Log.i("Check patient", "Start")
        val result = client.get(endpoint)

        Log.i("Checking Patient", result.status.toString())
        Log.i("Checking Patient", result.body())

        return result.body<CheckPatientResponse?>()
    }
}