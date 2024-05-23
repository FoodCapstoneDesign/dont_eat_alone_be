package io.junseok.mealmate.domain.memberwish.service;

import io.junseok.mealmate.domain.board.dto.response.BoardInfo;
import io.junseok.mealmate.domain.board.entity.Board;
import io.junseok.mealmate.domain.board.service.BoardService;
import io.junseok.mealmate.domain.member.entity.Member;
import io.junseok.mealmate.domain.member.service.MemberService;
import io.junseok.mealmate.domain.memberwish.dto.response.MemberWishList;
import io.junseok.mealmate.domain.memberwish.entity.MemberWish;
import io.junseok.mealmate.domain.memberwish.repository.WishRepository;
import io.junseok.mealmate.exception.ErrorCode;
import io.junseok.mealmate.exception.MealMateException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WishService {

    private final WishRepository wishRepository;
    private final MemberService memberService;
    private final BoardService boardService;

    @Transactional
    public Long createWishList(Long boardId, String email) {
        Member member = memberService.getMember(email);
        Board board = boardService.findBoard(boardId);

        if (wishRepository.existsByBoardAndMember(board, member)) {
            throw new MealMateException(ErrorCode.EXIST_WISHLIST);
        }

        MemberWish memberWish = MemberWish.builder()
            .board(board)
            .member(member)
            .build();
        return wishRepository.save(memberWish).getMemberWishId();
    }

    @Transactional
    public MemberWishList getWishList(String email) {
        Member member = memberService.getMember(email);
        List<BoardInfo> infoList = wishRepository.findAllByMember(member)
            .stream()
            .map(wishList -> BoardInfo.builder()
                .title(wishList.getBoard().getTitle())
                .content(wishList.getBoard().getContent())
                .email(wishList.getMember().getEmail())
                .lastTime(wishList.getBoard().getCreateDt())
                .modifyDt(wishList.getBoard().getModifyDt())
                .build()
            )
            .toList();

        return MemberWishList.builder()
            .boardInfo(infoList)
            .build();
    }

    @Transactional
    public void remove(Long wishListId) {
        if(wishRepository.existsById(wishListId)){
            throw new MealMateException(ErrorCode.NOT_EXIST_WISHLIST);
        }

        wishRepository.deleteById(wishListId);
    }

    @Transactional(readOnly = true)
    public Integer showWishListCount(String email) {
        Member member = memberService.getMember(email);
        return wishRepository.countByMember(member);
    }
}
