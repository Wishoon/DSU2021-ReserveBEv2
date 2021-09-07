package com.dsu.industry.domain.reserve.exception;

import com.dsu.industry.global.error.ErrorCode;
import com.dsu.industry.global.error.exception.EntityNotFoundException;

public class ReserveNotFoundException extends EntityNotFoundException {
    public ReserveNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ReserveNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ReserveNotFoundException() {
        super(ErrorCode.RESERVE_NOT_FOUND);
    }
}
