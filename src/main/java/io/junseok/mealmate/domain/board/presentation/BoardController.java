package io.junseok.mealmate.domain.board.presentation;

import io.junseok.mealmate.domain.board.dto.request.BoardCreate;
import io.junseok.mealmate.domain.board.dto.response.BoardInfo;
import io.junseok.mealmate.domain.board.service.BoardService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@CrossOrigin
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Long> writeBoard(@RequestBody BoardCreate boardCreate, Principal principal){
        return ResponseEntity.ok(boardService.createBoard(boardCreate,principal.getName()));
    }

    /**
     * NOTE
     * 게시판 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<BoardInfo>> getBoardList(){
        return ResponseEntity.ok(boardService.showBoardList());
    }

    /**
     * NOTE
     * 단일 게시판 조회
     */
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardInfo> findBoard(@PathVariable Long boardId){
        return ResponseEntity.ok(boardService.getBoard(boardId));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId){
        boardService.remove(boardId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<Long> modifyBoard(
        @PathVariable Long boardId,
        @RequestBody BoardCreate boardCreate
    ){
        return ResponseEntity.ok(boardService.edit(boardId,boardCreate));
    }
}
