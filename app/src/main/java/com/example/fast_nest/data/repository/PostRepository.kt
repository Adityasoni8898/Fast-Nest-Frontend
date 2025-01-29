package com.example.fast_nest.data.repository

import android.content.Context
import com.example.fast_nest.data.api.ApiService
import com.example.fast_nest.data.api.RetrofitInstance
import com.example.fast_nest.data.models.Post
import com.example.fast_nest.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody


import com.example.fast_nest.data.api.VoteRequest
import kotlinx.coroutines.withContext

class PostRepository(context: Context) {
    private val api: ApiService = RetrofitInstance.getInstance(context)
    private val postDao = AppDatabase.getDatabase(context).postDao()

    suspend fun fetchPosts(): List<Post>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getPosts()
                if (response.isSuccessful) {
                    response.body()?.let { posts ->
                        postDao.insertAll(posts) // Save to RoomDB
                        return@withContext posts
                    }
                }
            } catch (e: Exception) {
                return@withContext null
            }
            return@withContext null
        }
    }

    suspend fun getCachedPosts(): List<Post> {
        return withContext(Dispatchers.IO) {
            postDao.getAllPosts()
        }
    }

    suspend fun voteOnPost(postId: Int, voteDir: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.voteOnPost(VoteRequest(postId, voteDir))
                response.isSuccessful
            } catch (e: Exception) {
                false
            }
        }
    }

    suspend fun createPost(title: RequestBody, content: RequestBody, image: MultipartBody.Part?): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.createPost(title, content, image)
                response.isSuccessful
            } catch (e: Exception) {
                false
            }
        }
    }
}