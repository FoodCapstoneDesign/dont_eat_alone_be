package io.junseok.mealmate.domain.membercategory.entity

import io.junseok.mealmate.domain.member.entity.Member
import javax.persistence.*

@Entity
@Table(name = "member_category")
class MemberCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_category_id")
    var memberCategoryId: Long? = null,

    @Column(name = "category_name")
    var categoryName: String,

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val member: Member
) {
    fun modifyCategoryName(categoryName: String) {
        this.categoryName = categoryName
    }
}
