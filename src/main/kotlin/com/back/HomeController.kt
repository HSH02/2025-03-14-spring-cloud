package com.back

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import software.amazon.awssdk.services.s3.S3Client

@RestController
class HomeController(
    private val s3Client: S3Client
    // s3 찾기 순서
    // 로그인
    // accessToekn
    // application
    // 환경변수
) {

    @GetMapping("/")
    fun main(): String {
        return "Hi"
    }

    @GetMapping("/s3-ls")
    fun buckets(): List<String> {
        return s3Client.listBuckets()
            .buckets()
            .map { it.name() }
    }

}