package com.example.diavantagemobile.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diavantagemobile.util.LoginState
import com.example.diavantagemobile.util.api.DiaVantageApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking


class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState : StateFlow<LoginState> = _uiState.asStateFlow()

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
        if (api.authenticate(inputUsername, inputPassword)){
            _uiState.update { currentState -> currentState.copy(isLogged = true, username = inputUsername) }
            return@runBlocking true
        }
        return@runBlocking false

    }

}