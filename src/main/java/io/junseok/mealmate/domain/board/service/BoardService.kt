package io.junseok.mealmate.domain.board.service

import io.junseok.mealmate.domain.board.dto.request.BoardCreate
import io.junseok.mealmate.domain.board.dto.response.BoardInfo
import io.junseok.mealmate.domain.board.dto.response.toCreateBoardInfo
import io.junseok.mealmate.domain.board.entity.Board
import io.junseok.mealmate.domain.board.repository.BoardRepository
import io.junseok.mealmate.domain.member.service.MemberService
import io.junseok.mealmate.exception.ErrorCode
import io.junseok.mealmate.exception.MealMateException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val memberService: MemberService
) {
    @Transactional
    fun createBoard(boardCreate: BoardCreate, email: String): Long {
        val member = memberService.getMember(email)
        val board = Board(
            title = boardCreate.title,
            content = boardCreate.content,
            member = member
        )

        return boardRepository.save(board).boardId!!
    }

    @Transactional(readOnly = true)
    fun showBoardList(): List<BoardInfo> {
        return boardRepository.findAllByOrderByCreateDtDesc()
            .map { it.toCreateBoardInfo() }
    }

    @Transactional
    fun remove(boardId: Long) {
        boardRepository.deleteById(boardId)
    }

    @Transactional
    fun edit(boardId: Long, boardCreate: BoardCreate): Long {
        val board = findBoard(boardId)
        board.updateBoard(boardCreate)
        return boardId
    }

    fun findBoard(boardId: Long): Board {
        return boardRepository.findById(boardId)
            .orElseThrow { MealMateException(ErrorCode.NOT_EXIST_BOARD) }
    }

    fun getBoard(boardId: Long): BoardInfo {
        val board = findBoard(boardId)
        /*Member member = memberService.getMember(email);
        if(board.getMember().getMemberId().equals(member.getMemberId())){
            return getBoardInfo(board, true);
        }
        return getBoardInfo(board, false);*/
        return board.toCreateBoardInfo()
    }
}
