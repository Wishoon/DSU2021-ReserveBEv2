package com.dsu.industry.domain.product.exception;

import com.dsu.industry.global.error.ErrorCode;
import com.dsu.industry.global.error.exception.EntityNotFoundException;

public class CategoryNotFoundException extends EntityNotFoundException {
    public CategoryNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public CategoryNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CategoryNotFoundException() {
        super(ErrorCode.NOT_EXIST_CATEGORY);
    }
}
