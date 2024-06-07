package io.junseok.mealmate.domain.board.dto.response;

import io.junseok.mealmate.domain.board.entity.Board;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record BoardInfo(
    String title,
    String content,
    String email,
    LocalDateTime lastTime,
    LocalDateTime modifyDt,
    long boardId
) {
    public static BoardInfo fromEntity(Board board){
        return BoardInfo.builder()
            .title(board.getTitle())
            .content(board.getContent())
            .email(board.getMember().getEmail())
            .lastTime(board.createDt)
            .modifyDt(board.modifyDt)
            .boardId(board.getBoardId())
            .build();
    }
}
