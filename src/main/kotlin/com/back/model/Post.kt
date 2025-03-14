package com.back.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class Post(
    @Id
    var id: String = "",
    var title: String = "",
    var content: String = "",
    var author: String = "",
    var imageUrl: String? = null,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()
) 