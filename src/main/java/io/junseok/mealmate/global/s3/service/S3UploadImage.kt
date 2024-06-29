package io.junseok.mealmate.global.s3.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import io.junseok.mealmate.exception.ErrorCode
import io.junseok.mealmate.exception.MealMateException
import io.junseok.mealmate.global.s3.dto.S3Response
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*


@Service
@RequiredArgsConstructor
class S3UploadImage(
    private val amazonS3: AmazonS3Client,
    @Value("\${cloud.aws.credentials.bucket}")
    private val bucket: String
) {

    fun saveImage(file: MultipartFile): S3Response {
        try {
            val fileName = UUID.randomUUID().toString()

            val metadata = ObjectMetadata()
            metadata.contentType = file.contentType
            metadata.contentLength = file.size

            amazonS3.putObject(bucket, fileName, file.inputStream, metadata)
            val imageUrl = amazonS3.getUrl(bucket, fileName).toString()
            return S3Response(
                imageUrl=imageUrl,
                fileName=fileName
            )
        } catch (e: IOException) {
            throw MealMateException(ErrorCode.EXIST_EMAIL)
        }
    }
}
