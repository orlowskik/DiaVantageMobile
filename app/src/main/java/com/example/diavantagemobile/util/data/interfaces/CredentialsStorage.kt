package com.example.diavantagemobile.util.data.interfaces

import com.example.diavantagemobile.util.data.credentials.Credentials


interface CredentialsStorage {

    fun putCredentials(credentials: Credentials)

    fun getCredentials(): Credentials

    fun removeCredentials()

}