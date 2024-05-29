package io.junseok.mealmate.domain.board.service;

import io.junseok.mealmate.domain.board.dto.request.BoardCreate;
import io.junseok.mealmate.domain.board.dto.response.BoardInfo;
import io.junseok.mealmate.domain.board.entity.Board;
import io.junseok.mealmate.domain.board.repository.BoardRepository;
import io.junseok.mealmate.domain.member.entity.Member;
import io.junseok.mealmate.domain.member.service.MemberService;
import io.junseok.mealmate.exception.ErrorCode;
import io.junseok.mealmate.exception.MealMateException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberService memberService;
    @Transactional
    public Long createBoard(BoardCreate boardCreate, String email) {
        Member member = memberService.getMember(email);
        Board board = Board.builder()
            .title(boardCreate.title())
            .content(boardCreate.content())
            .member(member)
            .build();

        return boardRepository.save(board).getBoardId();
    }

    @Transactional(readOnly = true)
    public List<BoardInfo> showBoardList() {
        return boardRepository.findAllByOrderByCreateDt().stream()
            .map(BoardInfo::fromEntity)
            .toList();
    }

    @Transactional
    public void remove(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    @Transactional
    public Long edit(Long boardId,BoardCreate boardCreate) {
        Board board = findBoard(boardId);
        board.updateBoard(boardCreate);
        return boardId;
    }

    public Board findBoard(Long boardId) {
        return boardRepository.findById(boardId)
            .orElseThrow(() -> new MealMateException(ErrorCode.NOT_EXIST_BOARD));
    }

    public BoardInfo getBoard(Long boardId) {
        Board board = findBoard(boardId);
        return BoardInfo.builder()
            .title(board.getTitle())
            .content(board.getContent())
            .lastTime(board.createDt)
            .modifyDt(board.modifyDt)
            .build();
    }
}
