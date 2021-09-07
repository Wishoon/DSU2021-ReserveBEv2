package com.dsu.industry.domain.product.exception;

import com.dsu.industry.global.error.ErrorCode;
import com.dsu.industry.global.error.exception.EntityNotFoundException;

public class AvailableDateNotFoundException extends EntityNotFoundException {

    public AvailableDateNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public AvailableDateNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AvailableDateNotFoundException() {
        super(ErrorCode.NOT_AVAILABLE_PRODUCT);
    }
}
