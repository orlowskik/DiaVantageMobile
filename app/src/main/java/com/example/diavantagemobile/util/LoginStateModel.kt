package com.example.diavantagemobile.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginStateModel {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun loginUser(username: String?, password: String?, token: String?) {
        _loginState.update { currentState ->
            currentState.copy(isLogged = true, username = username, password = password, authToken = token) }
    }

    fun logoutUser() {
        _loginState.value = LoginState()
    }



}