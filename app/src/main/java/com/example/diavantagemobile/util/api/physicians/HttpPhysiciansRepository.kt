package com.example.diavantagemobile.util.api.physicians

import android.util.Log
import com.example.diavantagemobile.util.api.responses.PhysicianResponse
import com.example.diavantagemobile.util.data.ApiStrings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class HttpPhysiciansRepository(
    val client: HttpClient,
    val apiStrings: ApiStrings = ApiStrings(),
) : PhysiciansRepository {
    override suspend fun getPhysicians(): List<PhysicianResponse> {
        Log.i("Get Physicians", "Starting physicians retrieve")

        val response = client.get(apiStrings.physicians)
        Log.i("Get Physicians", response.status.toString())
        Log.i("Get Physicians", response.body())


        return response.body<List<PhysicianResponse>>()
    }
}