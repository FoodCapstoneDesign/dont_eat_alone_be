package io.junseok.mealmate.domain.board.presentation

import io.junseok.mealmate.domain.board.dto.request.BoardCreate
import io.junseok.mealmate.domain.board.dto.response.BoardInfo
import io.junseok.mealmate.domain.board.service.BoardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/board")
@CrossOrigin
class BoardController(
    private val boardService: BoardService
) {

    @PostMapping
    fun writeBoard(
        @RequestBody boardCreate: BoardCreate,
        principal: Principal
    ): ResponseEntity<Long> =
        ResponseEntity.ok(boardService.createBoard(boardCreate, principal.name))

        /**
         * NOTE
         * 게시판 목록 조회
         */
    @GetMapping
    fun boardList(): ResponseEntity<List<BoardInfo>> =
        ResponseEntity.ok(boardService.showBoardList())


    /**
     * NOTE
     * 단일 게시판 조회
     */
    @GetMapping("/{boardId}")
    fun findBoard(@PathVariable boardId: Long): ResponseEntity<BoardInfo> =
        ResponseEntity.ok(boardService.getBoard(boardId))


    @DeleteMapping("/{boardId}")
    fun deleteBoard(@PathVariable boardId: Long): ResponseEntity<Unit> {
        boardService.remove(boardId)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/{boardId}")
    fun modifyBoard(
        @PathVariable boardId: Long,
        @RequestBody boardCreate: BoardCreate
    ): ResponseEntity<Long> =
        ResponseEntity.ok(boardService.edit(boardId, boardCreate))
}
