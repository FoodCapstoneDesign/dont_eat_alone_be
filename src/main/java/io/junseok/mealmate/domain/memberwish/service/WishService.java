package io.junseok.mealmate.domain.memberwish.service;

import io.junseok.mealmate.domain.member.entity.Member;
import io.junseok.mealmate.domain.member.service.MemberService;
import io.junseok.mealmate.domain.memberwish.dto.response.MemberWishList;
import io.junseok.mealmate.domain.memberwish.entity.MemberWish;
import io.junseok.mealmate.domain.memberwish.repository.WishRepository;
import io.junseok.mealmate.domain.restaurant.dto.response.RestaurantInfo;
import io.junseok.mealmate.domain.restaurant.entity.Restaurant;
import io.junseok.mealmate.domain.restaurant.service.RestaurantService;
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
    private final RestaurantService restaurantService;
    @Transactional
    public Long createWishList(Long restaurantId, String email) {
        Member member = memberService.getMember(email);
        Restaurant restaurant = restaurantService.findByRestaurantId(restaurantId);

        if (wishRepository.existsByRestaurantAndMember(restaurant, member)) {
            throw new MealMateException(ErrorCode.EXIST_WISHLIST);
        }

        MemberWish memberWish = MemberWish.builder()
            .restaurant(restaurant)
            .member(member)
            .build();
        return wishRepository.save(memberWish).getMemberWishId();
    }

    @Transactional
    public MemberWishList getWishList(String email) {
        Member member = memberService.getMember(email);
        List<RestaurantInfo> infoList = wishRepository.findAllByMember(member)
            .stream()
            .map(MemberWishList::fromRestaurantInfo)
            .toList();

        return MemberWishList.builder()
            .restaurantInfoList(infoList)
            .build();
    }

    @Transactional
    public void remove(Long restaurantId,String email) {
        Member member = memberService.getMember(email);
        Restaurant restaurant = restaurantService.findByRestaurantId(restaurantId);
        if(wishRepository.existsByRestaurantAndMember(restaurant,member)){
            throw new MealMateException(ErrorCode.NOT_EXIST_WISHLIST);
        }

        wishRepository.deleteByRestaurantAndMember(restaurant,member);
    }

    @Transactional(readOnly = true)
    public Integer showWishListCount(String email) {
        Member member = memberService.getMember(email);
        return wishRepository.countByMember(member);
    }
}
