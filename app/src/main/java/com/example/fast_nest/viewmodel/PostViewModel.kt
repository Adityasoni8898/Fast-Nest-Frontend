package com.example.fast_nest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fast_nest.data.models.Post
import com.example.fast_nest.data.repository.PostRepository
import kotlinx.coroutines.launch

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val postRepository = PostRepository(application)

    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = _posts

    fun fetchPosts() {
        viewModelScope.launch {
            val response = postRepository.fetchPosts() ?: postRepository.getCachedPosts()
            _posts.postValue(response)
        }
    }

    private val _postCreationStatus = MutableLiveData<Boolean>()
    val postCreationStatus: LiveData<Boolean> get() = _postCreationStatus

    fun createPost(title: RequestBody, content: RequestBody, image: MultipartBody.Part?) {
        viewModelScope.launch {
            val success = postRepository.createPost(title, content, image)
            _postCreationStatus.postValue(success)
        }
    }

    private val _voteStatus = MutableLiveData<Boolean>()
    val voteStatus: LiveData<Boolean> get() = _voteStatus

    fun voteOnPost(postId: Int, voteDir: Int) {
        viewModelScope.launch {
            val success = postRepository.voteOnPost(postId, voteDir)
            _voteStatus.postValue(success)
        }
    }
}