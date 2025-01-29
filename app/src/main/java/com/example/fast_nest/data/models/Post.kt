package com.example.fast_nest.data.models

data class Post(
    val id: String,
    val title: String,
    val content: String,
    val imageUrl: String?, // Nullable in case no image is uploaded
    val createdAt: String,
    val userId: String
)