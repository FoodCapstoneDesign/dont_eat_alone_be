package io.junseok.mealmate.domain.comment.service;

import io.junseok.mealmate.domain.board.entity.Board;
import io.junseok.mealmate.domain.board.service.BoardService;
import io.junseok.mealmate.domain.comment.dto.request.CommentWrite;
import io.junseok.mealmate.domain.comment.dto.response.CommentInfo;
import io.junseok.mealmate.domain.comment.entity.Comment;
import io.junseok.mealmate.domain.comment.repository.CommentRepository;
import io.junseok.mealmate.domain.member.entity.Member;
import io.junseok.mealmate.domain.member.service.MemberService;
import io.junseok.mealmate.exception.ErrorCode;
import io.junseok.mealmate.exception.MealMateException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final BoardService boardService;

    @Transactional
    public Long create(Long boardId, CommentWrite commentWrite, String email) {
        Member member = memberService.getMember(email);
        Board board = boardService.findBoard(boardId);
        Comment comment = Comment.builder()
            .commentContent(commentWrite.commentContent())
            .member(member)
            .board(board)
            .build();
        return commentRepository.save(comment).getCommentId();
    }

    @Transactional(readOnly = true)
    public List<CommentInfo> showComments(Long boardId) {
        Board board = boardService.findBoard(boardId);
        return commentRepository.findAllByBoard(board)
            .stream()
            .map(CommentInfo::fromEntity)
            .toList();
    }

    @Transactional
    public void updateComment(Long commentId, String email, String commentContent) {
        Comment comment = getComment(commentId);
        log.info(commentContent);
        if(!comment.getMember().getEmail().equals(email)){
            throw new MealMateException(ErrorCode.NOT_WRITE_MEMBER);
        }
        log.info(commentContent);
        comment.update(commentContent);
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new MealMateException(ErrorCode.NOT_EXIST_COMMENT));
    }

    @Transactional
    public void remove(Long commentId, String email) {
        Comment comment = getComment(commentId);
        if(comment.getMember().getEmail().equals(email)){
            commentRepository.delete(comment);
        }
    }
}
