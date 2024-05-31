package io.junseok.mealmate.domain.comment.dto.response;

import io.junseok.mealmate.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CommentInfo(
    String commentContent,
    String email,
    long commentId,
    LocalDateTime createDt,
    LocalDateTime modifyDt
) {
    public static CommentInfo fromEntity(Comment comment){
        return CommentInfo.builder()
            .commentContent(comment.getCommentContent())
            .email(comment.getMember().getEmail())
            .commentId(comment.getCommentId())
            .createDt(comment.getCreateDt())
            .modifyDt(comment.getModifyDt())
            .build();
    }
}

