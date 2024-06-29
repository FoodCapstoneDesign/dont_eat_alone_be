package io.junseok.mealmate.domain.board.dto.response

import io.junseok.mealmate.domain.board.entity.Board
import java.time.LocalDateTime

data class BoardInfo(
    val title: String,
    val content: String,
    val email: String,
    val lastTime: LocalDateTime,
    val modifyDt: LocalDateTime,
    val boardId: Long
)

fun Board.toCreateBoardInfo() = BoardInfo(
    title = this.title,
    content = this.content,
    email = this.member.email,
    lastTime = this.createDt!!,
    modifyDt = this.modifyDt!!,
    boardId = this.boardId!!
)
