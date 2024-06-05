package io.junseok.mealmate.domain.memberwish.dto.response;

import io.junseok.mealmate.domain.memberwish.entity.MemberWish;
import io.junseok.mealmate.domain.restaurant.dto.response.RestaurantInfo;
import java.util.LinkedList;
import java.util.List;
import lombok.Builder;

@Builder
public record MemberWishList(List<RestaurantInfo> restaurantInfoList) {
    public static RestaurantInfo fromRestaurantInfo(MemberWish memberWish){
        return RestaurantInfo.builder()
            .restaurantId(memberWish.getRestaurant().getRestaurantId())
            .restaurantName(memberWish.getRestaurant().getRestaurantName())
            .restaurantImageUrl(memberWish.getRestaurant().getRestaurantImageUrl())
            .restaurantType(memberWish.getRestaurant().getRestaurantType())
            .likeCount(memberWish.getRestaurant().getLikeCount())
            .build();
    }
}