package io.junseok.mealmate.domain.comment.presentation;

import io.junseok.mealmate.domain.comment.dto.request.CommentUpdate;
import io.junseok.mealmate.domain.comment.dto.request.CommentWrite;
import io.junseok.mealmate.domain.comment.dto.response.CommentInfo;
import io.junseok.mealmate.domain.comment.service.CommentService;
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
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@CrossOrigin
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{boardId}")
    public ResponseEntity<Long> writeComment(
        @PathVariable Long boardId,
        @RequestBody CommentWrite commentWrite,
        Principal principal
    ){
        return ResponseEntity.ok(commentService.create(boardId,commentWrite,principal.getName()));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<List<CommentInfo>> getCommentList(@PathVariable Long boardId){
        return ResponseEntity.ok(commentService.showComments(boardId));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Long> modifyComment(
        @PathVariable Long commentId,
        @RequestBody CommentUpdate commentUpdate,
        Principal principal
    ){
        commentService.updateComment(commentId,principal.getName(),commentUpdate.commentContent());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,Principal principal){
        commentService.remove(commentId,principal.getName());
        return ResponseEntity.ok().build();
    }
}
