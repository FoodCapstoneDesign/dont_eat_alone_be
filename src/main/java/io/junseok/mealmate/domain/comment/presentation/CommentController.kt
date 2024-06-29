package io.junseok.mealmate.domain.comment.presentation

import io.junseok.mealmate.domain.comment.dto.request.CommentUpdate
import io.junseok.mealmate.domain.comment.dto.request.CommentWrite
import io.junseok.mealmate.domain.comment.dto.response.CommentInfo
import io.junseok.mealmate.domain.comment.service.CommentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/comment")
@CrossOrigin
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping("/{boardId}")
    fun writeComment(
        @PathVariable boardId: Long,
        @RequestBody commentWrite: CommentWrite,
        principal: Principal
    ): ResponseEntity<Long> =
        ResponseEntity.ok(commentService.create(boardId, commentWrite, principal.name))


    @GetMapping("/{boardId}")
    fun getCommentList(@PathVariable boardId: Long): ResponseEntity<List<CommentInfo>> =
        ResponseEntity.ok(commentService.showComments(boardId))


    @PatchMapping("/{commentId}")
    fun modifyComment(
        @PathVariable commentId: Long,
        @RequestBody commentUpdate: CommentUpdate,
        principal: Principal
    ): ResponseEntity<Long> {
        commentService.updateComment(commentId, principal.name, commentUpdate.commentContent)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(@PathVariable commentId: Long, principal: Principal): ResponseEntity<Unit> {
        commentService.remove(commentId, principal.name)
        return ResponseEntity.ok().build()
    }
}
