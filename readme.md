# 🚀 Fast-Nest: A Minimal Social Media App

Fast-Nest is a lightweight social media app built using **Android Kotlin (MVVM)** with **FastAPI** as the backend. It enables users to create accounts, post updates with optional images, like posts, and engage in discussions. The app uses **JWT-based authentication**, **Retrofit for API calls**, **RoomDB for caching**, and **Glide for image loading**.

## 🌟 Features
✅ **User Authentication** (Register, Login, Logout)  
✅ **Create, Update, and Delete Posts**  
✅ **Like/Dislike Posts**  
✅ **Image Uploads (Camera/Gallery)**  
✅ **JWT-Based Authentication**  
✅ **Offline Support with RoomDB**  
✅ **User Profile Page**  

---

## 📂 Project Structure

FastNest/
│── app/
│   ├── src/main/
│   │   ├── java/com/example/fast_nest/
│   │   │   ├── data/
│   │   │   │   ├── api/               # Retrofit API service
│   │   │   │   │   ├── ApiService.kt
│   │   │   │   │   ├── AuthInterceptor.kt
│   │   │   │   ├── models/            # Data Models (User, Post, Vote)
│   │   │   │   │   ├── User.kt
│   │   │   │   │   ├── Post.kt
│   │   │   │   │   ├── Vote.kt
│   │   │   │   ├── repository/        # Handles API & local data operations
│   │   │   │   │   ├── AuthRepository.kt
│   │   │   │   │   ├── PostRepository.kt
│   │   │   │   │   ├── UserRepository.kt
│   │   │   ├── db/                    # Room Database for offline support
│   │   │   │   ├── AppDatabase.kt
│   │   │   │   ├── TokenDao.kt
│   │   │   ├── ui/                    # UI Screens
│   │   │   │   ├── auth/              # Login & Signup Screens
│   │   │   │   │   ├── LoginActivity.kt
│   │   │   │   │   ├── RegisterActivity.kt
│   │   │   │   ├── main/              # Main Feed & Post Adapter
│   │   │   │   │   ├── MainActivity.kt
│   │   │   │   │   ├── PostAdapter.kt
│   │   │   │   ├── post/              # Create & View Post Screens
│   │   │   │   │   ├── CreatePostActivity.kt
│   │   │   │   │   ├── PostDetailActivity.kt
│   │   │   ├── viewmodel/             # ViewModel for MVVM
│   │   │   │   ├── AuthViewModel.kt
│   │   │   │   ├── PostViewModel.kt
│   │   │   │   ├── UserViewModel.kt
│   │   │   ├── utils/                 # Utility functions
│   │   │   │   ├── Constants.kt
│   │   │   │   ├── PrefsManager.kt
│   │   │   │   ├── ImageUtils.kt
│   │   ├── res/
│   │   │   ├── layout/                # XML Layouts for UI Screens
│   │   │   │   ├── activity_login.xml
│   │   │   │   ├── activity_register.xml
│   │   │   │   ├── activity_main.xml
│   │   │   │   ├── activity_create_post.xml
│   │   │   │   ├── activity_post_detail.xml
│   │   │   │   ├── item_post.xml
│   │   │   ├── values/
│   │   │   │   ├── colors.xml
│   │   │   │   ├── strings.xml
│   │   │   │   ├── themes.xml

---

## 🔌 Backend API Endpoints
Fast-Nest interacts with a **FastAPI-based backend** hosted at `http://localhost:8000/`.  
Here are the key API endpoints:

| Method | Endpoint               | Description |
|--------|------------------------|-------------|
| POST   | `/login`               | Login (email/username + password) |
| POST   | `/api/user`            | Register new user (username, email, password) |
| GET    | `/api/user/{user_id}`  | Get user details |
| GET    | `/api/post?limit=20`   | Fetch all posts |
| POST   | `/api/post`            | Create a new post (title, content, optional image) |
| PUT    | `/api/post/{post_id}`  | Update an existing post |
| DELETE | `/api/post/{post_id}`  | Delete a post |
| POST   | `/api/vote`            | Like/Dislike a post (post_id, vote_dir) |

---

## 📲 Setup Instructions

### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/your_username/FastNest.git
cd FastNest