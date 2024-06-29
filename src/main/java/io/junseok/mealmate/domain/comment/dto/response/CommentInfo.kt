package io.junseok.mealmate.domain.comment.dto.response;

import io.junseok.mealmate.domain.comment.entity.Comment
import java.time.LocalDateTime

data class CommentInfo(
    val commentContent: String,
    val email: String,
    val commentId: Long,
    val createDt: LocalDateTime,
    val modifyDt: LocalDateTime
)

fun Comment.toCreateCommentInfo() = CommentInfo(
    commentContent = this.commentContent,
    email = this.member.email,
    commentId = this.commentId!!,
    createDt = this.createDt!!,
    modifyDt = this.modifyDt!!
)