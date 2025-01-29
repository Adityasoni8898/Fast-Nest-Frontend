package com.example.fast_nest.ui.post

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fast_nest.R
import com.example.fast_nest.viewmodel.PostViewModel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class CreatePostActivity : AppCompatActivity() {
    private lateinit var etTitle: EditText
    private lateinit var etContent: EditText
    private lateinit var ivPostImage: ImageView
    private lateinit var btnSelectImage: Button
    private lateinit var btnCreatePost: Button

    private val postViewModel: PostViewModel by viewModels()
    private var imageUri: Uri? = null
    private var selectedImageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        etTitle = findViewById(R.id.etTitle)
        etContent = findViewById(R.id.etContent)
        ivPostImage = findViewById(R.id.ivPostImage)
        btnSelectImage = findViewById(R.id.btnSelectImage)
        btnCreatePost = findViewById(R.id.btnCreatePost)

        btnSelectImage.setOnClickListener {
            selectImageFromGallery()
        }

        btnCreatePost.setOnClickListener {
            createPost()
        }

        postViewModel.postCreationStatus.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Post created successfully!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to create post", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            imageUri?.let {
                ivPostImage.visibility = ImageView.VISIBLE
                Glide.with(this).load(it).into(ivPostImage)
                selectedImageFile = File(getRealPathFromURI(it))
            }
        }
    }

    private fun getRealPathFromURI(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        val path = cursor?.getString(columnIndex!!)
        cursor?.close()
        return path ?: ""
    }

    private fun createPost() {
        val title = etTitle.text.toString()
        val content = etContent.text.toString()

        if (title.isBlank() || content.isBlank()) {
            Toast.makeText(this, "Title and content cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val titlePart = RequestBody.create("text/plain".toMediaTypeOrNull(), title)
        val contentPart = RequestBody.create("text/plain".toMediaTypeOrNull(), content)

        val imagePart = selectedImageFile?.let { file ->
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            MultipartBody.Part.createFormData("image", file.name, requestFile)
        }

        postViewModel.createPost(titlePart, contentPart, imagePart)
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}