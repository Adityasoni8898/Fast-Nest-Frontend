package com.example.fast_nest.ui.post

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fast_nest.R
import com.example.fast_nest.data.models.Post
import com.example.fast_nest.viewmodel.PostViewModel

class PostDetailActivity : AppCompatActivity() {
    private lateinit var tvPostTitle: TextView
    private lateinit var tvPostContent: TextView
    private lateinit var ivPostImage: ImageView
    private lateinit var btnUpvote: Button
    private lateinit var btnDownvote: Button

    private val postViewModel: PostViewModel by viewModels()
    private var postId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        tvPostTitle = findViewById(R.id.tvPostTitle)
        tvPostContent = findViewById(R.id.tvPostContent)
        ivPostImage = findViewById(R.id.ivPostImage)
        btnUpvote = findViewById(R.id.btnUpvote)
        btnDownvote = findViewById(R.id.btnDownvote)

        val post = intent.getParcelableExtra<Post>("post")
        post?.let {
            tvPostTitle.text = it.title
            tvPostContent.text = it.content
            postId = it.id

            if (!it.imageUrl.isNullOrEmpty()) {
                ivPostImage.visibility = ImageView.VISIBLE
                Glide.with(this).load(it.imageUrl).into(ivPostImage)
            }
        }

        btnUpvote.setOnClickListener { vote(1) }
        btnDownvote.setOnClickListener { vote(0) }

        postViewModel.voteStatus.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Vote submitted!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Voting failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun vote(voteDir: Int) {
        if (postId == -1) {
            Toast.makeText(this, "Invalid post ID", Toast.LENGTH_SHORT).show()
            return
        }
        postViewModel.voteOnPost(postId, voteDir)
    }
}