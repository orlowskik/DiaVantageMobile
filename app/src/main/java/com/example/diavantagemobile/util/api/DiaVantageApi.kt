package com.example.diavantagemobile.util.api

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.submitForm
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable


data class ApiStrings(
    val hostName: String = "http://192.168.1.56:8000/",
    val login: String = "rest-auth/login/",
    )

@Serializable
data class AuthToken(
    val key: String,
)

class DiaVantageApi(
    private val httpClient: HttpClient = HttpClient(CIO){
        install(ContentNegotiation){
            json()
        }
    },
    private val apiStrings: ApiStrings = ApiStrings(),
){



    suspend fun authenticate(username: String, password: String ){
        Log.i("Request status", "Starting response")
        val endpoint = apiStrings.hostName + apiStrings.login
        val response: HttpResponse = httpClient.submitForm(
            url = endpoint,
            formParameters = parameters {
                append("username", username)
                append("password", password)
            }
        )
        Log.i("Response status: ", response.status.toString())
        if (response.status == HttpStatusCode.OK){
            val authToken: AuthToken = response.body()
            Log.i("Token", authToken.toString())
        }




    }
}
