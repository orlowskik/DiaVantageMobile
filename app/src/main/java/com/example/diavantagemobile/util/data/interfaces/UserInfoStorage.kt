package com.example.diavantagemobile.util.data.interfaces

interface UserInfoStorage {
    fun updateInfo(
        inputUsername: String?,
        inputPassword: String?,
        inputPatientId: String?,
    )

    fun getInfo(): Map<String,String?>
}