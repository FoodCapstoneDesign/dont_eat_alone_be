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
    NOT_EXIST_COMMENT(HttpStatusCode.BAD_REQUEST.getStatus(), "존재하지 않는 댓글입니다!"),
    NOT_WRITE_MEMBER(HttpStatusCode.BAD_REQUEST.getStatus(),"댓글을 작성한 사용자가 아닙니다!"),
    NOT_EXIST_RESTAURANT(HttpStatusCode.BAD_REQUEST.getStatus(),"존재하지 않는 식당입니다!"),

    NOT_EXIST_AUTHENTICATION(HttpStatusCode.UNAUTHORIZED.getStatus(), "Security Context에 인증 정보가 없습니다!"),
    NOT_AUTHENTICATION(HttpStatusCode.UNAUTHORIZED.getStatus(), "게시판 삭제 권한이 없습니다!"),
    INVALID_EMAIL_FORMAT(HttpStatusCode.BAD_REQUEST.getStatus(),"이메일 형식이 올바르지 않습니다"),
    EXIST_LIKE_COUNT(HttpStatusCode.CONFLICT.getStatus(), "이미 좋아요를 눌렀습니다!");
    private final int status;
    private final String message;
}
