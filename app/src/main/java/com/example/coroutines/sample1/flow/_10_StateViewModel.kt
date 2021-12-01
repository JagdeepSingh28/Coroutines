package com.example.coroutines.sample1.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class _10_StateViewModel : ViewModel() {

    private val _loginUiState = MutableStateFlow<LoginUIState>(LoginUIState.Empty)
    val loginUIState : StateFlow<LoginUIState> = _loginUiState

    fun login(userName : String, password: String) = viewModelScope.launch {
        _loginUiState.value = LoginUIState.Loading
        delay(2000)
        if(userName.equals("android") && password.equals("password"))
            _loginUiState.value = LoginUIState.Success
        else
            _loginUiState.value = LoginUIState.Error("Wrong Credentials")
    }

    sealed class LoginUIState{
        object Success : LoginUIState()
        data class Error(val message: String) : LoginUIState()
        object Loading : LoginUIState()
        object Empty : LoginUIState()

    }

}