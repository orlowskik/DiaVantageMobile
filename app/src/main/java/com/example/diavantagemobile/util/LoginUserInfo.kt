package com.example.diavantagemobile.util

import com.example.diavantagemobile.util.data.interfaces.UserInfoStorage
import com.example.diavantagemobile.util.data.storages.RealLoginUserInfo

object LoginUserInfo {
    private val userInfoStorage: UserInfoStorage = RealLoginUserInfo()
    val userInfo: UserInfoProvider = UserInfoProvider(userInfoStorage)
}