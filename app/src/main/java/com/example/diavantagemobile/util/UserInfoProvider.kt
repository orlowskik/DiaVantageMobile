package com.example.diavantagemobile.util

import com.example.diavantagemobile.util.data.interfaces.UserInfoStorage

class UserInfoProvider (
    private val userInfoStorage: UserInfoStorage,
){

    fun getDistinctInfo(key: String): String?{
        return userInfoStorage.getInfo()[key]
    }

    fun getInfo(): Map<String, String>{
        return userInfoStorage.getInfo()
    }

    fun updateUserInfo(inputUsername: String?, inputPassword: String?, inputPatientId: String?){
        userInfoStorage.updateInfo(inputUsername, inputPassword, inputPatientId)
    }

}