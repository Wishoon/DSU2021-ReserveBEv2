package com.dsu.industry.domain.review.exception;

import com.dsu.industry.global.error.ErrorCode;
import com.dsu.industry.global.error.exception.EntityNotFoundException;

public class ReviewNotFoundException extends EntityNotFoundException {
    public ReviewNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ReviewNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ReviewNotFoundException() {
        super(ErrorCode.NOT_EXIST_REVIEW);
    }
}
