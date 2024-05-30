package io.junseok.mealmate.domain.comment.dto.response;

import io.junseok.mealmate.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CommentInfo(
    String commentContent,
    String email,
    LocalDateTime createDt,
    LocalDateTime modifyDt
) {
    public static CommentInfo fromEntity(Comment comment){
        return CommentInfo.builder()
            .commentContent(comment.getCommentContent())
            .email(comment.getMember().getEmail())
            .createDt(comment.getCreateDt())
            .modifyDt(comment.getModifyDt())
            .build();
    }
}
