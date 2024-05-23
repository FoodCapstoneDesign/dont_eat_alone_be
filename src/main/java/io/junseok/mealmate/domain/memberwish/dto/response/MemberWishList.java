package io.junseok.mealmate.domain.memberwish.dto.response;

import io.junseok.mealmate.domain.board.dto.response.BoardInfo;
import java.util.List;
import lombok.Builder;

@Builder
public record MemberWishList(List<BoardInfo> boardInfo) {

}
