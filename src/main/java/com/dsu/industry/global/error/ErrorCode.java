package com.dsu.industry.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
    BAD_INPUT_REQUEST(400, "COMMON_001", "입력값이 잘못되었습니다."),
    VALIDATION_ERROR(400, "COMMON_OO4", "입력값에 대한 검증을 만족하지 못했습니다 - "),
    METHOD_NOT_ALLOWED(405, "COMMON_002", "HTTP 메서드가 잘못되었습니다."),
    SERVER_ERROR(500, "COMMON_003", "서버 오류입니다."),

    // User
    EMAIL_DUPLICATION(400, "USER_001", "중복된 이메일(아이디) 입니다."),
    USER_NOT_FOUND(404, "USER_002", "등록된 유저가 아닙니다."),

    // Product
    NOT_AVAILABLE_PRODUCT(400, "PRODUCT_001", "예약이 불가능한 날짜 입니다."),
    NOT_EXIST_PRODUCT(400, "PRODUCT_002", "존재하지 않는 상품입니다."),

    // Auth
    AUTHENTICATION_FAILED(403, "AUTH_001", "인증에 실패하였습니다."),
    PASSWORD_FAILED(400, "AUTH_002", "이메일(아이디) 혹은 비밀번호가 틀립니다."),
    INVALID_JWT_TOKEN(403, "AUTH_003", "유효하지 않은 토큰입니다."),
    EXPIRED_JWT_TOKEN(403, "AUTH_004", "만료된 토큰입니다."),
    ACCESS_DENIED(403, "AUTH_005", "접근 권한이 없습니다."),
    NOT_JWT_TOKEN(403, "AUTH_006", "토큰이 존재하지 않습니다.");

    // InvalidValue

    private int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
