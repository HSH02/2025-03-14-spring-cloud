package com.back

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.*

@Service
class S3ServiceImpl (
    private val s3Client: S3Client,
    @Value("\${aws.s3.name}") private val bucketName: String
) : S3Service {

    override fun getBucketNames(): List<String> {
        return s3Client.listBuckets().buckets().map { it.name() }
    }

    fun uploadImage(imageBytes: ByteArray, contentType: String): String {
        val key = "images/${UUID.randomUUID()}"
        
        val request = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .contentType(contentType)
            .build()
        
        s3Client.putObject(request, RequestBody.fromBytes(imageBytes))
        
        return "https://${bucketName}.s3.amazonaws.com/${key}"
    }
    
    fun deleteObject(key: String) {
        val request = DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build()
            
        s3Client.deleteObject(request)
    }
}