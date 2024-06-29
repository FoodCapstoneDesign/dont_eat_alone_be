package io.junseok.mealmate.domain.comment.service

import io.junseok.mealmate.domain.board.service.BoardService
import io.junseok.mealmate.domain.comment.dto.request.CommentWrite
import io.junseok.mealmate.domain.comment.dto.response.CommentInfo
import io.junseok.mealmate.domain.comment.dto.response.toCreateCommentInfo
import io.junseok.mealmate.domain.comment.entity.Comment
import io.junseok.mealmate.domain.comment.repository.CommentRepository
import io.junseok.mealmate.domain.member.service.MemberService
import io.junseok.mealmate.exception.ErrorCode
import io.junseok.mealmate.exception.MealMateException
import lombok.extern.slf4j.Slf4j
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Slf4j
class CommentService(
    private val commentRepository: CommentRepository,
    private val memberService: MemberService,
    private val boardService: BoardService
) {
    @Transactional
    fun create(boardId: Long, commentWrite: CommentWrite, email: String): Long {
        val member = memberService.getMember(email)
        val board = boardService.findBoard(boardId)

        return Comment(
            commentContent = commentWrite.commentContent,
            member = member,
            board = board
        ).also {
            commentRepository.save(it)
        }.commentId!!
    }

    @Transactional(readOnly = true)
    fun showComments(boardId: Long): List<CommentInfo> {
        val board = boardService.findBoard(boardId)
        return commentRepository.findAllByBoard(board)
            .map { it.toCreateCommentInfo() }
    }

    @Transactional
    fun updateComment(commentId: Long, email: String, commentContent: String) {
        val comment = getComment(commentId)
        if (comment.member.email != email) {
            throw MealMateException(ErrorCode.NOT_WRITE_MEMBER)
        }
        comment.update(commentContent)
    }

    private fun getComment(commentId: Long): Comment =
        commentRepository.findByIdOrNull(commentId)
            ?: throw MealMateException(ErrorCode.NOT_EXIST_COMMENT)

    @Transactional
    fun remove(commentId: Long, email: String) {
        val comment = getComment(commentId)
        if (comment.member.email == email) {
            commentRepository.delete(comment)
        }
    }
}
