package com.dsu.industry.global.error.exception;

import com.dsu.industry.global.error.ErrorCode;

public class InvalidException extends BusinessException {


    public InvalidException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public InvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
