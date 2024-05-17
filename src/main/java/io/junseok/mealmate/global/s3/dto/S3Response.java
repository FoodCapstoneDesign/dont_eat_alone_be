package io.junseok.mealmate.global.s3.dto;

import lombok.Builder;

@Builder
public record S3Response(
    String imageUrl,
    String fileName
) {

}
