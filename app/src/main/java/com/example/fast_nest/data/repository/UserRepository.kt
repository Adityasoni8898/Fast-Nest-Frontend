package com.example.fast_nest.data.repository

import android.content.Context
import com.example.fast_nest.data.api.ApiService
import com.example.fast_nest.data.api.RetrofitInstance
import com.example.fast_nest.data.api.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(context: Context) {
    private val api: ApiService = RetrofitInstance.getInstance(context)

    suspend fun getUserById(userId: Int): UserResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getUserById(userId)
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                null
            }
        }
    }
}