package com.example.diavantagemobile.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.diavantagemobile.util.api.DiaVantageApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking


data class LoginUiState(
    val username: String? = null,
    val isLogged: Boolean = false,
    val authToken: String? = null,
)


class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState : StateFlow<LoginUiState> = _uiState.asStateFlow()

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
        api.authenticate(inputUsername, inputPassword)
    }

}