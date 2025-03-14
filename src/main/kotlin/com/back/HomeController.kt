package com.back

import com.back.model.Post
import com.back.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class HomeController(
    private val s3ServiceImpl: S3ServiceImpl,
    private val postService: PostService
) {

    @GetMapping("/")
    fun main(): String {
        return "안녕하세요! 게시판 API에 오신 것을 환영합니다."
    }

    @GetMapping("/buckets")
    fun buckets(): List<String> {
        return s3ServiceImpl.getBucketNames()
    }
    
    // 게시글 관련 CRUD 엔드포인트
    @GetMapping("/posts")
    fun getAllPosts(): List<Post> {
        return postService.getAllPosts()
    }
    
    @GetMapping("/posts/{id}")
    fun getPost(@PathVariable id: String): ResponseEntity<Post> {
        return try {
            ResponseEntity.ok(postService.getPostById(id))
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }
    
    @PostMapping("/posts")
    fun createPost(@RequestBody post: Post): ResponseEntity<Post> {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(post))
    }
    
    @PutMapping("/posts/{id}")
    fun updatePost(@PathVariable id: String, @RequestBody post: Post): ResponseEntity<Post> {
        return try {
            ResponseEntity.ok(postService.updatePost(id, post))
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }
    
    @DeleteMapping("/posts/{id}")
    fun deletePost(@PathVariable id: String): ResponseEntity<Void> {
        return try {
            postService.deletePost(id)
            ResponseEntity.noContent().build()
        } catch (e: Exception) {
            ResponseEntity.notFound().build()
        }
    }
    
    // 이미지 업로드 엔드포인트
    @PostMapping("/posts/{id}/image", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadImage(
        @PathVariable id: String,
        @RequestParam("image") file: MultipartFile
    ): ResponseEntity<Map<String, String>> {
        val imageUrl = postService.uploadImage(id, file.bytes, file.contentType ?: "application/octet-stream")
        return ResponseEntity.ok(mapOf("imageUrl" to imageUrl))
    }
}