package com.example.fast_nest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fast_nest.data.models.Token

@Dao
interface TokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToken(token: Token)

    @Query("SELECT * FROM token LIMIT 1")
    suspend fun getToken(): Token?

    @Query("DELETE FROM token")
    suspend fun clearToken()
}