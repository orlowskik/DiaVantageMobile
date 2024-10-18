package com.example.diavantagemobile.util.api.login

import android.util.Log
import com.example.diavantagemobile.util.data.ApiStrings
import com.example.diavantagemobile.util.data.interfaces.CredentialsStorage
import com.example.diavantagemobile.util.data.interfaces.LogoutRepository
import com.example.diavantagemobile.util.data.responses.CsrfResponse
import com.example.diavantagemobile.util.data.responses.LogoutResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.cookie
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

class HttpLogoutRepository(
    private val client: HttpClient,
    private val credentialsStorage: CredentialsStorage,
    private val apiStrings: ApiStrings = ApiStrings()
) : LogoutRepository{
    override suspend fun logout(): Boolean {
        val token = client.post(apiStrings.getCSRF).body<CsrfResponse>()

        Log.i("Logout", "Starting logout")
        val response = client.post(apiStrings.logout){
            cookie(name = "csrftoken", value = token.CSRFToken)
            header("X-CSRFToken",token.CSRFToken)
        }


        Log.i("Logout", response.status.toString())
        Log.i("Logout", response.body())
        val result = response.body<LogoutResponse?>()
        result?.let {
            if (it.detail == "Successfully logged out.") {
                credentialsStorage.removeCredentials()
                return true
            }
            return false
        } ?: return false
    }
}