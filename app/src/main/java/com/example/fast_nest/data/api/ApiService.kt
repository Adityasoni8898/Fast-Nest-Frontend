package com.example.fast_nest.data.api

import com.example.fast_nest.data.models.Post
import com.example.fast_nest.data.models.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

import retrofit2.http.Body
import retrofit2.http.POST

import retrofit2.http.Path

data class VoteRequest(val post_id: Int, val vote_dir: Int)



interface ApiService {
    // User Authentication
    @POST("login")
    suspend fun login(@Body loginRequest: Map<String, String>): Response<ResponseBody>

    @POST("user")
    suspend fun register(@Body user: User): Response<ResponseBody>

    // Posts
    @GET("/api/post")
    suspend fun getPosts(@Query("limit") limit: Int = 20): Response<List<Post>>

    @Multipart
    @POST("/api/post")
    suspend fun createPost(
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<Unit>

    @POST("/api/vote")
    suspend fun voteOnPost(@Body voteRequest: VoteRequest): Response<Unit>

    @GET("/api/user/{user_id}")
    suspend fun getUserById(@Path("user_id") userId: Int): Response<UserResponse>
}

data class UserResponse(
    val id: Int,
    val username: String,
    val email: String
)