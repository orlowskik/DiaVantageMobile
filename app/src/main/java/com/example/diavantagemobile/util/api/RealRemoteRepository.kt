package com.example.diavantagemobile.util.api

import com.example.diavantagemobile.util.api.glucose.HttpGlucoseRepository
import com.example.diavantagemobile.util.api.login.HttpCheckPatientRepository
import com.example.diavantagemobile.util.api.login.HttpLoginRepository
import com.example.diavantagemobile.util.api.login.HttpLogoutRepository
import com.example.diavantagemobile.util.data.ApiStrings
import com.example.diavantagemobile.util.data.interfaces.CheckPatientRepository
import com.example.diavantagemobile.util.data.interfaces.CredentialsStorage
import com.example.diavantagemobile.util.data.interfaces.GlucoseRepository
import com.example.diavantagemobile.util.data.interfaces.LoginRepository
import com.example.diavantagemobile.util.data.interfaces.LogoutRepository
import com.example.diavantagemobile.util.data.interfaces.RemoteRepository
import com.example.diavantagemobile.util.data.storages.BasicCredentialStorage
import io.ktor.client.HttpClient

internal class RealRemoteRepository(
    private val client: HttpClient,
    private val credentialStorage: CredentialsStorage,
    private val apiStrings: ApiStrings = ApiStrings(),
) : RemoteRepository{
    override fun loginRepository(): LoginRepository = HttpLoginRepository(client, credentialStorage, apiStrings.login )
    override fun logoutRepository(): LogoutRepository = HttpLogoutRepository(client, credentialStorage, apiStrings)
    override fun checkPatientRepository(): CheckPatientRepository = HttpCheckPatientRepository(client, apiStrings.checkPatient)
    override fun glucoseRepository(): GlucoseRepository = HttpGlucoseRepository(client, apiStrings)
}