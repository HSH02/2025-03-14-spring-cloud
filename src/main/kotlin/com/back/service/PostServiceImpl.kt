package com.back.service

import com.back.S3ServiceImpl
import com.back.model.Post
import com.back.repository.PostRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val s3ServiceImpl: S3ServiceImpl
) : PostService {

    override fun getAllPosts(): List<Post> {
        return postRepository.findAll()
    }

    override fun getPostById(id: String): Post {
        return postRepository.findById(id)
            .orElseThrow { EntityNotFoundException("게시글을 찾을 수 없습니다: $id") }
    }

    override fun createPost(post: Post): Post {
        return postRepository.save(post)
    }

    override fun updatePost(id: String, post: Post): Post {
        val existingPost = getPostById(id)

        existingPost.title = post.title
        existingPost.content = post.content
        existingPost.author = post.author
        existingPost.updatedAt = LocalDateTime.now()

        if (post.imageUrl != null) {
            existingPost.imageUrl = post.imageUrl
        }

        return postRepository.save(existingPost)
    }

    override fun deletePost(id: String) {
        val post = getPostById(id)

        // 연결된 S3 이미지가 있다면 삭제
        if (post.imageUrl != null) {
            val key = post.imageUrl!!.substringAfterLast("/")
            s3ServiceImpl.deleteObject(key)
        }

        postRepository.deleteById(id)
    }

    override fun uploadImage(id: String, imageBytes: ByteArray, contentType: String): String {
        val post = getPostById(id)
        val key = "posts/$id/${UUID.randomUUID()}"

        val imageUrl = s3ServiceImpl.uploadImage(imageBytes, contentType)
        post.imageUrl = imageUrl
        postRepository.save(post)

        return imageUrl
    }
} 