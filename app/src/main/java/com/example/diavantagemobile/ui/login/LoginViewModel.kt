package com.example.diavantagemobile.ui.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diavantagemobile.ui.interfaces.APIViewModel
import com.example.diavantagemobile.util.api.login.CheckPatientRepository
import com.example.diavantagemobile.util.api.login.LoginRepository
import kotlinx.coroutines.runBlocking


class LoginViewModel : APIViewModel() {

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

    fun authenticateUser(loginRepository: LoginRepository) = runBlocking{
        try {
            val result = loginRepository.login(inputUsername, inputPassword)
            result?.let {
                Log.i("Authenticate user", (it.key is String).toString())
                return@runBlocking it.key != null } ?: return@runBlocking false
        } catch (e: Exception){
            Log.e("Error", e.toString())
            errorName = e.javaClass.name
            errorMessage = e.message.toString()
            toggleErrorDialog()
            return@runBlocking false
        }

    }

    fun checkPatientStatus(checkPatientRepository: CheckPatientRepository) = runBlocking {
        val result = checkPatientRepository.checkPatient()
        return@runBlocking result?.patient_id
    }


    fun resetUserInput(){
        inputUsername = ""
        inputPassword = ""

    }


}