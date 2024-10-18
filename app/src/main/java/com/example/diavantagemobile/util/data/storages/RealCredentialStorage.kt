package com.example.diavantagemobile.util.data.storages

import com.example.diavantagemobile.util.data.credentials.BasicCredentials
import com.example.diavantagemobile.util.data.credentials.Credentials
import com.example.diavantagemobile.util.data.interfaces.CredentialsStorage

internal class BasicCredentialStorage : CredentialsStorage{
    private val basicCredentials = mutableSetOf<BasicCredentials>()

    override fun putCredentials(credentials: Credentials) {
        basicCredentials.add(credentials as BasicCredentials)
    }

    override fun getCredentials(): Credentials {
        if (basicCredentials.isEmpty()){
            putCredentials(BasicCredentials("", ""))
        }
        return basicCredentials.last()
    }

    override fun removeCredentials(){
        basicCredentials.last().also { basicCredentials.remove(it) }
    }


}