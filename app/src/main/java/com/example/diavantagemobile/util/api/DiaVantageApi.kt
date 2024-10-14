package com.example.diavantagemobile.util.api

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BasicAuthCredentials
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.http.HttpStatusCode
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable


data class ApiStrings(
    val hostName: String = "https://diavantage.azurewebsites.net//",
    val login: String = "rest-auth/login/",
    val logout: String = "rest-auth/logout/",
    val patientRegister: String = "register/patient/",
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
    val apiStrings: ApiStrings = ApiStrings(),
) {


    suspend fun authenticate(username: String, password: String): Boolean {
        Log.i("Request status", "Starting login")
        val endpoint = apiStrings.hostName + apiStrings.login
        val response: HttpResponse = httpClient.submitForm(
            url = endpoint,
            formParameters = parameters {
                append("username", username)
                append("password", password)
            }
        )
        Log.i("Response status: ", response.status.toString())
        if (response.status == HttpStatusCode.OK) {
            val authToken: AuthToken = response.body()
            updateClientCredentials(username, password)
            Log.i("Token", authToken.toString())
            return true
        }
        return false
    }

    suspend fun logout(): Boolean{
        Log.i("Request status", "Starting logout")
        val endpoint = apiStrings.hostName + apiStrings.logout
        val response: HttpResponse = httpClient.post(endpoint)
        Log.i("Response status: ", response.status.toString())

        if (response.status == HttpStatusCode.OK) {
            Log.i("Logout", "Successful")
            updateClientCredentials("", "")
            return true
        }
        return false
    }

    private fun updateClientCredentials(username: String, password: String) {
        httpClient.config {
            install(Auth) {
                basic {
                    credentials {
                        BasicAuthCredentials(username = username, password = password)
                    }
                }
            }
        }
    }
}
