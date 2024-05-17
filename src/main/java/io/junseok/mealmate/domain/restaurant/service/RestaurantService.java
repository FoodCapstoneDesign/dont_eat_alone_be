package io.junseok.mealmate.domain.restaurant.service;

import io.junseok.mealmate.domain.restaurant.dto.request.RestaurantRegister;
import io.junseok.mealmate.domain.restaurant.dto.response.RestaurantDetailInfo;
import io.junseok.mealmate.domain.restaurant.dto.response.RestaurantInfo;
import io.junseok.mealmate.domain.restaurant.entity.Restaurant;
import io.junseok.mealmate.domain.restaurant.repository.RestaurantRepository;
import io.junseok.mealmate.domain.restaurantmenu.dto.response.MenuInfo;
import io.junseok.mealmate.domain.restaurantmenu.repository.RestaurantMenuRepository;
import io.junseok.mealmate.exception.ErrorCode;
import io.junseok.mealmate.exception.MealMateException;
import io.junseok.mealmate.global.s3.dto.S3Response;
import io.junseok.mealmate.global.s3.service.S3UploadImage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private static final Integer INITIAL_COUNT=0;
    private static final Double INITIAL_GRADE=0.0;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMenuRepository restaurantMenuRepository;
    private final S3UploadImage s3UploadImage;

    @Transactional
    public Long createRestaurant(RestaurantRegister restaurantRegister, MultipartFile file) {
        if (restaurantRepository.existsByRestaurantName(restaurantRegister.restaurantName())){
            throw new MealMateException(ErrorCode.EXIST_EMAIL);
        }
        S3Response s3Response = s3UploadImage.saveImage(file);
        Restaurant restaurant = Restaurant.builder()
            .restaurantName(restaurantRegister.restaurantName())
            .restaurantImageUrl(s3Response.imageUrl())
            .restaurantFileName(s3Response.fileName())
            .restaurantType(restaurantRegister.restaurantType())
            .likeCount(INITIAL_COUNT)
            .location(restaurantRegister.location())
            .telNum(restaurantRegister.telNum())
            .openAt(restaurantRegister.openAt())
            .closeAt(restaurantRegister.closeAt())
            .restaurantNotice(restaurantRegister.restaurantNotice())
            .grade(INITIAL_GRADE)
            .build();

        return restaurantRepository.save(restaurant).getRestaurantId();
    }

    @Transactional
    public void removeRestaurant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    @Transactional(readOnly = true)
    public List<RestaurantInfo> findRestaurants(String restaurantType) {
        return restaurantRepository.findAllByRestaurantType(restaurantType)
            .stream()
            .map(RestaurantInfo::fromEntity)
            .toList();
    }

    public List<RestaurantInfo> showBestRestaurants() {
        return restaurantRepository.find3TopByLikeCount()
            .stream()
            .map(RestaurantInfo::fromEntity)
            .toList();
    }

    @Transactional(readOnly = true)
    public RestaurantDetailInfo findRestaurantInfo(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new MealMateException(ErrorCode.EXIST_EMAIL));

        List<MenuInfo> menuInfoList = restaurantMenuRepository.findAllByRestaurant(restaurant)
            .stream()
            .map(MenuInfo::fromEntity)
            .toList();

        return RestaurantDetailInfo.builder()
            .restaurantName(restaurant.getRestaurantName())
            .restaurantImageUrl(restaurant.getRestaurantImageUrl())
            .restaurantType(restaurant.getRestaurantType())
            .restaurantNotice(restaurant.getRestaurantNotice())
            .likeCount(restaurant.getLikeCount())
            .grade(restaurant.getGrade())
            .openAt(restaurant.getOpenAt())
            .closeAt(restaurant.getCloseAt())
            .menuList(menuInfoList)
            .build();
    }
}
