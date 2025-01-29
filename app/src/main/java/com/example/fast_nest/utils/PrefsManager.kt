package com.example.fast_nest.utils

import android.content.Context
import android.content.SharedPreferences

class PrefsManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(Constants.JWT_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(Constants.JWT_TOKEN, null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove(Constants.JWT_TOKEN).apply()
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences("FastNestPrefs", Context.MODE_PRIVATE)

    fun saveUserId(userId: Int) {
        prefs.edit().putInt("USER_ID", userId).apply()
    }

    fun getUserId(): Int {
        return prefs.getInt("USER_ID", -1)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}