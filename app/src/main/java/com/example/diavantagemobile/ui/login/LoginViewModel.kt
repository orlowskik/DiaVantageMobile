package com.example.diavantagemobile.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diavantagemobile.util.api.DiaVantageApi
import kotlinx.coroutines.runBlocking


class LoginViewModel : ViewModel() {


    var inputUsername by mutableStateOf("")
        private set

    var inputPassword by mutableStateOf("")
        private set

    fun updateUsername(username: String){
        inputUsername = username
    }

    fun updatePassword(password: String){
        inputPassword = password
    }

    fun authenticateUser(api: DiaVantageApi) = runBlocking{
        val result = api.authenticate(inputUsername, inputPassword)
        return@runBlocking result
    }

    fun resetUserInput(){
        inputUsername = ""
        inputPassword = ""

    }


}