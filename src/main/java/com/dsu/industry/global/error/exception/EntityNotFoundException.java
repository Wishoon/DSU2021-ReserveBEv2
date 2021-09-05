package com.dsu.industry.global.error.exception;

import com.dsu.industry.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
