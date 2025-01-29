package com.example.fast_nest.data.repository

import android.content.Context
import com.example.fast_nest.data.api.ApiService
import com.example.fast_nest.data.api.RetrofitInstance
import com.example.fast_nest.data.models.Token
import com.example.fast_nest.db.AppDatabase
import com.example.fast_nest.utils.PrefsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response

class AuthRepository(context: Context) {
    private val api: ApiService = RetrofitInstance.getInstance(context)
    private val tokenDao = AppDatabase.getDatabase(context).tokenDao()
    private val prefsManager = PrefsManager(context)

    suspend fun login(usernameOrEmail: String, password: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.login(
                    mapOf("username_or_email" to usernameOrEmail, "password" to password)
                )
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    val json = JSONObject(responseBody ?: "")
                    val token = json.getString("token")

                    // Save token in RoomDB and SharedPreferences
                    tokenDao.saveToken(Token(token = token))
                    prefsManager.saveToken(token)

                    token
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun register(user: Map<String, String>): Response<Unit> {
        return withContext(Dispatchers.IO) {
            api.register(user)
        }
    }
}