package io.junseok.mealmate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EXIST_EMAIL(HttpStatusCode.BAD_REQUEST.getStatus(), "이미 존재하는 Email 입니다!"),
    EXIST_WISHLIST(HttpStatusCode.CONFLICT.getStatus(), "이미 찜 하였습니다!"),

    NOT_EXIST_ADMIN(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 관리자입니다!"),
    NOT_EXIST_BOARD(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 게시판입니다!"),
    NOT_EXIST_WISHLIST(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 찜한 게시판입니다!"),

    NOT_EXIST_AUTHENTICATION(HttpStatusCode.UNAUTHORIZED.getStatus(), "Security Context에 인증 정보가 없습니다!"),
    NOT_AUTHENTICATION(HttpStatusCode.UNAUTHORIZED.getStatus(), "게시판 삭제 권한이 없습니다!"),
    EXIST_LIKE_COUNT(HttpStatusCode.CONFLICT.getStatus(), "이미 좋아요를 눌렀습니다!");
    private final int status;
    private final String message;
}
