package io.junseok.mealmate.global.s3.service

import com.amazonaws.services.s3.AmazonS3Client
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class S3DeleteImage(
    private val amazonS3: AmazonS3Client,
    @Value("\${cloud.aws.credentials.bucket}")
    private val bucket: String
) {
    fun deleteImage(fileName: String?) {
        amazonS3.deleteObject(bucket, fileName)
    }
}
