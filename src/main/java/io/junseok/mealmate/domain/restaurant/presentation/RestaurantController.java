package io.junseok.mealmate.domain.restaurant.presentation;

import io.junseok.mealmate.domain.restaurant.dto.request.RestaurantRegister;
import io.junseok.mealmate.domain.restaurant.dto.response.RestaurantDetailInfo;
import io.junseok.mealmate.domain.restaurant.dto.response.RestaurantInfo;
import io.junseok.mealmate.domain.restaurant.service.RestaurantService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
@CrossOrigin
public class RestaurantController {

    private final RestaurantService restaurantService;

    /**
     * 식당 정보 등록
     */
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> registerRestaurant(
        @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
        @RequestPart(value = "restaurantRegister") RestaurantRegister restaurantRegister) {
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantRegister, imageFile));
    }

    /**
     * 식당 정보 삭제
     */
    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long restaurantId) {
        restaurantService.removeRestaurant(restaurantId);
        return ResponseEntity.ok().build();
    }

    /**
     * 검색한 타입에 맞는 식당 목록 반환
     */
    @GetMapping
    public ResponseEntity<List<RestaurantInfo>> getRestaurantList(
        @RequestParam(value = "type") String restaurantType
    ) {
        return ResponseEntity.ok(restaurantService.findRestaurants(restaurantType));
    }

    @GetMapping("/best")
    public ResponseEntity<List<RestaurantInfo>> getBestRestaurantList(){
        return ResponseEntity.ok(restaurantService.showBestRestaurants());
    }

    /**
     * 식당 세부정보 조회
     */
    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDetailInfo> getRestaurantInfo(@PathVariable Long restaurantId){
        return ResponseEntity.ok(restaurantService.findRestaurantInfo(restaurantId));
    }
}
