package com.example.fast_nest.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Token(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val token: String
)