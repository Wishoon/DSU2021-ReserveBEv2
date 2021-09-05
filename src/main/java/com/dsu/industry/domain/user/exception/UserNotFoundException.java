package com.dsu.industry.domain.user.exception;

import com.dsu.industry.global.error.ErrorCode;
import com.dsu.industry.global.error.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
