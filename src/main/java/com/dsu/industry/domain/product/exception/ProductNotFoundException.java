package com.dsu.industry.domain.product.exception;

import com.dsu.industry.global.error.ErrorCode;
import com.dsu.industry.global.error.exception.EntityNotFoundException;

public class ProductNotFoundException extends EntityNotFoundException {
    public ProductNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ProductNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ProductNotFoundException() {
        super(ErrorCode.NOT_EXIST_PRODUCT);
    }
}
