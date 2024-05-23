package io.junseok.mealmate.domain.board.entity;

import io.junseok.mealmate.domain.base.BaseTimeEntity;
import io.junseok.mealmate.domain.board.dto.request.BoardCreate;
import io.junseok.mealmate.domain.member.entity.Member;
import io.junseok.mealmate.domain.member.service.MemberService;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateBoard(BoardCreate boardCreate){
        this.title=boardCreate.title();
        this.content=boardCreate.content();
    }
}
