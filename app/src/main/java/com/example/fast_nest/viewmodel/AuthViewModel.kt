package com.example.fast_nest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fast_nest.data.repository.AuthRepository
import kotlinx.coroutines.launch


class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val authRepository = AuthRepository(application)

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> get() = _loginStatus

    private val _registrationStatus = MutableLiveData<Boolean>()
    val registrationStatus: LiveData<Boolean> get() = _registrationStatus

    fun login(usernameOrEmail: String, password: String) {
        viewModelScope.launch {
            val token = authRepository.login(usernameOrEmail, password)
            _loginStatus.postValue(token != null)
        }
    }

    fun register(user: Map<String, String>) {
        viewModelScope.launch {
            val response = authRepository.register(user)
            _registrationStatus.postValue(response.isSuccessful)
        }
    }
}