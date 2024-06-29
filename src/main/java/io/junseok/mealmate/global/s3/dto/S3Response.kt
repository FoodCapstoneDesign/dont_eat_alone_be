package io.junseok.mealmate.global.s3.dto

import lombok.Builder

@Builder
data class S3Response(
    val imageUrl: String,
    val fileName: String
)
