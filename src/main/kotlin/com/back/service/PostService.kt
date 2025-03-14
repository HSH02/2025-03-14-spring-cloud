package com.back.service

import com.back.model.Post
import org.springframework.stereotype.Service

interface PostService {
    fun getAllPosts(): List<Post>
    fun getPostById(id: String): Post
    fun createPost(post: Post): Post
    fun updatePost(id: String, post: Post): Post
    fun deletePost(id: String)
    fun uploadImage(id: String, imageBytes: ByteArray, contentType: String): String
} 