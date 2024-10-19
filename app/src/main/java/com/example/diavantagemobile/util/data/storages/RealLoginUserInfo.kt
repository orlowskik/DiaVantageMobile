package com.example.diavantagemobile.util.data.storages

import androidx.compose.runtime.mutableStateOf
import com.example.diavantagemobile.util.LoginUserInfo
import com.example.diavantagemobile.util.data.interfaces.UserInfoStorage

internal class RealLoginUserInfo: UserInfoStorage {

    private var username = mutableStateOf<String?>(null)
    private var password = mutableStateOf<String?>(null)
    private var patientId = mutableStateOf<String?>(null)

    override fun updateInfo(inputUsername: String?, inputPassword: String?, inputPatientId: String?) {
        username.value = inputUsername
        password.value = inputPassword
        patientId.value = inputPatientId
    }

    override fun getInfo(): Map<String, String?> {
        return mapOf(
            "username" to username.value,
            "password" to password.value,
            "patientId" to patientId.value,
        )
    }


}