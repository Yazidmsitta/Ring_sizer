package com.ringsize.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ringsize.app.data.repository.AuthRepository
import com.ringsize.app.util.Result
import com.ringsize.app.util.SharedPreferencesManager
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val prefsManager: SharedPreferencesManager
) : ViewModel() {
    
    private val _loginResult = MutableLiveData<Result<Unit>>()
    val loginResult: LiveData<Result<Unit>> = _loginResult
    
    private val _registerResult = MutableLiveData<Result<Unit>>()
    val registerResult: LiveData<Result<Unit>> = _registerResult
    
    private val _logoutResult = MutableLiveData<Result<Unit>>()
    val logoutResult: LiveData<Result<Unit>> = _logoutResult
    
    private val _forgotPasswordResult = MutableLiveData<Result<Unit>>()
    val forgotPasswordResult: LiveData<Result<Unit>> = _forgotPasswordResult
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = Result.Loading
            when (val result = authRepository.login(email, password)) {
                is Result.Success -> {
                    try {
                        prefsManager.saveAuthToken(result.data.token)
                        prefsManager.saveUserId(result.data.user.id)
                        prefsManager.saveUserName(result.data.user.name)
                        prefsManager.saveUserEmail(result.data.user.email)
                        _loginResult.value = Result.Success(Unit)
                    } catch (e: Exception) {
                        _loginResult.value = Result.Error(Exception("Erreur lors de la sauvegarde des données: ${e.message}"))
                    }
                }
                is Result.Error -> {
                    _loginResult.value = Result.Error(result.exception)
                }
                else -> {}
            }
        }
    }
    
    fun register(name: String, email: String, password: String, passwordConfirmation: String) {
        viewModelScope.launch {
            _registerResult.value = Result.Loading
            when (val result = authRepository.register(name, email, password, passwordConfirmation)) {
                is Result.Success -> {
                    try {
                        prefsManager.saveAuthToken(result.data.token)
                        prefsManager.saveUserId(result.data.user.id)
                        prefsManager.saveUserName(result.data.user.name)
                        prefsManager.saveUserEmail(result.data.user.email)
                        _registerResult.value = Result.Success(Unit)
                    } catch (e: Exception) {
                        _registerResult.value = Result.Error(Exception("Erreur lors de la sauvegarde des données: ${e.message}"))
                    }
                }
                is Result.Error -> {
                    _registerResult.value = Result.Error(result.exception)
                }
                else -> {}
            }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            val token = prefsManager.getAuthToken()
            if (token != null) {
                _logoutResult.value = Result.Loading
                authRepository.logout(token)
            }
            prefsManager.clearUserData()
            _logoutResult.value = Result.Success(Unit)
        }
    }
    
    fun forgotPassword(email: String) {
        viewModelScope.launch {
            _forgotPasswordResult.value = Result.Loading
            when (val result = authRepository.forgotPassword(email)) {
                is Result.Success -> {
                    _forgotPasswordResult.value = Result.Success(Unit)
                }
                is Result.Error -> {
                    _forgotPasswordResult.value = Result.Error(result.exception)
                }
                else -> {}
            }
        }
    }
    
    fun isLoggedIn(): Boolean {
        return prefsManager.getAuthToken() != null
    }
}






