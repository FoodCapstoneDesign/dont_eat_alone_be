package io.junseok.mealmate.domain.memberwish.presentation;

import io.junseok.mealmate.domain.memberwish.dto.response.MemberWishList;
import io.junseok.mealmate.domain.memberwish.service.WishService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wish")
@RequiredArgsConstructor
@CrossOrigin
public class WishController {
    private final WishService wishService;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Long> saveWishList(@PathVariable Long restaurantId, Principal principal){
        return ResponseEntity.ok(wishService.createWishList(restaurantId,principal.getName()));
    }

    @GetMapping
    public ResponseEntity<MemberWishList> getMemberWishLists(Principal principal){
        return ResponseEntity.ok(wishService.getWishList(principal.getName()));
    }

    @DeleteMapping("/{wishListId}")
    public ResponseEntity<Void> deleteWishList(@PathVariable Long wishListId){
        wishService.remove(wishListId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getWishListCount(Principal principal){
        return ResponseEntity.ok(wishService.showWishListCount(principal.getName()));
    }
}
