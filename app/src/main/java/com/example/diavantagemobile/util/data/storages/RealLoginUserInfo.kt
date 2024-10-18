package com.example.diavantagemobile.util.data.storages

import androidx.compose.runtime.mutableStateOf
import com.example.diavantagemobile.util.LoginUserInfo
import com.example.diavantagemobile.util.data.interfaces.UserInfoStorage

internal class RealLoginUserInfo: UserInfoStorage {

    private var username = mutableStateOf("")
    private var password = mutableStateOf("")
    private var patientId = mutableStateOf("")

    override fun updateInfo(inputUsername: String?, inputPassword: String?, inputPatientId: String?) {
        if (inputUsername != null) {
            username.value = inputUsername
        }
        if (inputPassword != null) {
            password.value = inputPassword
        }
        if (inputPatientId != null) {
            patientId.value = inputPatientId
        }
    }

    override fun getInfo(): Map<String,String> {
        return mapOf(
            "username" to username.value,
            "password" to password.value,
            "patientId" to patientId.value,
        )
    }


}