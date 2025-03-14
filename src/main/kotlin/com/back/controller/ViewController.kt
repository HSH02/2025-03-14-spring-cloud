package com.back.controller

import com.back.model.Post
import com.back.service.PostService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@Controller
@RequestMapping("/view")
class ViewController(private val postService: PostService) {

    @GetMapping
    fun listPosts(model: Model): String {
        model.addAttribute("posts", postService.getAllPosts())
        model.addAttribute("newPost", Post("", "", "", ""))
        return "posts/list"
    }

    @GetMapping("/create")
    fun createPostForm(model: Model): String {
        model.addAttribute("post", Post("", "", "", ""))
        return "posts/create"
    }

    @PostMapping("/create")
    fun createPost(@ModelAttribute post: Post): String {
        postService.createPost(post)
        return "redirect:/view"
    }

    @GetMapping("/{id}")
    fun viewPost(@PathVariable id: String, model: Model): String {
        model.addAttribute("post", postService.getPostById(id))
        return "posts/view"
    }

    @GetMapping("/{id}/edit")
    fun editPostForm(@PathVariable id: String, model: Model): String {
        model.addAttribute("post", postService.getPostById(id))
        return "posts/edit"
    }

    @PostMapping("/{id}/edit")
    fun updatePost(@PathVariable id: String, @ModelAttribute post: Post): String {
        postService.updatePost(id, post)
        return "redirect:/view/${id}"
    }
    
    @PostMapping("/{id}/delete")
    fun deletePost(@PathVariable id: String): String {
        postService.deletePost(id)
        return "redirect:/view"
    }
    
    @PostMapping("/{id}/upload")
    fun uploadImage(@PathVariable id: String, @RequestParam("image") file: MultipartFile): String {
        if (!file.isEmpty) {
            postService.uploadImage(id, file.bytes, file.contentType ?: "application/octet-stream")
        }
        return "redirect:/view/${id}"
    }
} 