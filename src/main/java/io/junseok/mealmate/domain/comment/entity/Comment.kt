package io.junseok.mealmate.domain.comment.entity

import io.junseok.mealmate.domain.base.BaseTimeEntity
import io.junseok.mealmate.domain.board.entity.Board
import io.junseok.mealmate.domain.member.entity.Member
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@Table(name = "comment")
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    var commentId: Long? = null,

    @Column(name = "comment_content")
    var commentContent: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    val board: Board,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member
) : BaseTimeEntity() {
    fun update(commentContent: String) {
        this.commentContent = commentContent
    }
}

