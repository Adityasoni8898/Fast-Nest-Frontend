package com.example.fast_nest.ui.user

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fast_nest.R
import com.example.fast_nest.viewmodel.UserViewModel
import com.example.fast_nest.utils.PrefsManager
import com.example.fast_nest.ui.auth.LoginActivity

class UserProfileActivity : AppCompatActivity() {
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnEditProfile: Button
    private lateinit var btnLogout: Button

    private val userViewModel: UserViewModel by viewModels()
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        tvUsername = findViewById(R.id.tvUsername)
        tvEmail = findViewById(R.id.tvEmail)
        btnEditProfile = findViewById(R.id.btnEditProfile)
        btnLogout = findViewById(R.id.btnLogout)

        userId = PrefsManager(this).getUserId()
        if (userId != -1) {
            userViewModel.fetchUser(userId)
        }

        userViewModel.user.observe(this) { user ->
            if (user != null) {
                tvUsername.text = user.username
                tvEmail.text = user.email
            } else {
                Toast.makeText(this, "Failed to fetch user data", Toast.LENGTH_SHORT).show()
            }
        }

        btnEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            PrefsManager(this).clear()
            Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}