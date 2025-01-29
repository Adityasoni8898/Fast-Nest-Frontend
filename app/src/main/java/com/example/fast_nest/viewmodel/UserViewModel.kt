package com.example.fast_nest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fast_nest.data.repository.UserRepository
import com.example.fast_nest.data.api.UserResponse
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application)

    private val _user = MutableLiveData<UserResponse?>()
    val user: MutableLiveData<UserResponse?> get() = _user

    fun fetchUser(userId: Int) {
        viewModelScope.launch {
            val userResponse = userRepository.getUserById(userId)
            _user.postValue(userResponse)
        }
    }
}