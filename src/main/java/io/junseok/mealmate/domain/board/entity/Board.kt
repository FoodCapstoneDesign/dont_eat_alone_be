package io.junseok.mealmate.domain.board.entity

import io.junseok.mealmate.domain.base.BaseTimeEntity
import io.junseok.mealmate.domain.board.dto.request.BoardCreate
import io.junseok.mealmate.domain.member.entity.Member
import javax.persistence.*

@Entity
@Table(name = "board")
class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    var boardId: Long? = null,

    @Column(name = "title")
    var title: String,

    @Column(name = "content")
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member
) : BaseTimeEntity() {
    fun updateBoard(boardCreate: BoardCreate) {
        this.title = boardCreate.title
        this.content = boardCreate.content
    }
}
