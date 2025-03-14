//package com.back
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
//import software.amazon.awssdk.regions.Region
//import software.amazon.awssdk.services.s3.S3Client
//
//@Configuration
//class AwsConfig {
//
//    @Bean
//    fun s3Client(): S3Client {
//        return S3Client.builder()
//            .region(Region.AP_NORTHEAST_2) // 서울 리전 (필요에 따라 변경)
//            .credentialsProvider(DefaultCredentialsProvider.create())
//            .build()
//    }
//}