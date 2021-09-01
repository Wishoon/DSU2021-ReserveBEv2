package com.dsu.industry.domain.user.exception;

import com.dsu.industry.global.error.ErrorCode;
import com.dsu.industry.global.error.exception.InvalidException;

public class UserDuplicationException extends InvalidException {

    public UserDuplicationException() {
       super(ErrorCode.EMAIL_DUPLICATION);
    }

//    public UserDuplicationException(ErrorCode errorCode) {
//        super(errorCode);
//    }


}
