package io.junseok.mealmate.domain.board.service

import io.junseok.mealmate.domain.board.dto.request.BoardCreate
import io.junseok.mealmate.domain.board.dto.response.BoardInfo
import io.junseok.mealmate.domain.board.dto.response.toCreateBoardInfo
import io.junseok.mealmate.domain.board.entity.Board
import io.junseok.mealmate.domain.board.repository.BoardRepository
import io.junseok.mealmate.domain.member.service.MemberService
import io.junseok.mealmate.domain.restaurant.repository.RestaurantRepository
import io.junseok.mealmate.exception.ErrorCode
import io.junseok.mealmate.exception.MealMateException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val memberService: MemberService,
    private val restaurantRepository: RestaurantRepository
) {
    @Transactional
    fun createBoard(boardCreate: BoardCreate, email: String): Long {
        val member = memberService.getMember(email)
        val restaurant = (restaurantRepository.findByIdOrNull(boardCreate.restaurantId)
            ?: throw MealMateException(ErrorCode.NOT_EXIST_RESTAURANT))

        val board = Board(
            title = boardCreate.title,
            content = boardCreate.content,
            member = member,
            restaurant = restaurant
        )

        return boardRepository.save(board).boardId!!
    }

    @Transactional(readOnly = true)
    fun showBoardList(pageRequest: PageRequest): Page<BoardInfo> {
        return boardRepository.findAllByOrderByCreateDtDesc(pageRequest)
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
    @Transactional(readOnly = true)
    fun findBoard(boardId: Long): Board {
        return boardRepository.findById(boardId)
            .orElseThrow { MealMateException(ErrorCode.NOT_EXIST_BOARD) }
    }
    @Transactional(readOnly = true)
    fun getBoard(boardId: Long): BoardInfo {
        val board = findBoard(boardId)
        return board.toCreateBoardInfo()
    }

    @Transactional(readOnly = true)
    fun getBoardByRestaurant(restaurantId: Long, pageRequest: PageRequest): Page<BoardInfo>? {
        val restaurant = (restaurantRepository.findByIdOrNull(restaurantId)
            ?: throw MealMateException(ErrorCode.NOT_EXIST_RESTAURANT))
        return boardRepository.findAllByRestaurantOrderByCreateDtDesc(pageRequest,restaurant)
            .map { it.toCreateBoardInfo() }
    }
}
