package io.junseok.mealmate.global.s3.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config {
    @Value("\${cloud.aws.credentials.access-key}")
    private val accessKey: String? = null

    @Value("\${cloud.aws.credentials.secret-key}")
    private val secretKey: String? = null

    @Value("\${cloud.aws.credentials.region}")
    private val region: String? = null

    @Bean
    fun amazonS3Client(): AmazonS3Client {
        val credentials = BasicAWSCredentials(accessKey, secretKey)

        return AmazonS3ClientBuilder
            .standard()
            .withRegion(region)
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .build() as AmazonS3Client
    }
}