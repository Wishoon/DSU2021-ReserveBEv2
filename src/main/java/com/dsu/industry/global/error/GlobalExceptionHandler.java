package com.dsu.industry.global.error;

import com.dsu.industry.global.common.ErrorResponse;
import com.dsu.industry.global.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     *  javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할 경우
     *  주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("HandleMethodArgumentNotValidException", e);

        BindingResult bindingResult = e.getBindingResult();
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
        }

        return new ResponseEntity<>(ErrorResponse.builder()
                .code(ErrorCode.VALIDATION_ERROR.getCode())
                .message(bindingResult.toString())
                .status(ErrorCode.VALIDATION_ERROR.getStatus())
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * @ModelAttribute 으로 binding error 발생하는 경우
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.error("HandleBindException", e);

        BindingResult bindingResult = e.getBindingResult();
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
        }

        return new ResponseEntity<>(ErrorResponse.builder()
                .code(ErrorCode.BINDING_ERROR.getCode())
                .message(bindingResult.toString())
                .status(ErrorCode.BINDING_ERROR.getStatus())
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("HandleMethodArgumentTypeMismatchException", e);

        return new ResponseEntity<>(ErrorResponse.builder()
                .code(ErrorCode.BINDING_ERROR.getCode())
                .message(e.getMessage())
                .status(ErrorCode.VALIDATION_ERROR.getStatus())
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Spring Security 에러
     */
    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        log.info("HandleBadCredentialsException", ex);
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .code(ErrorCode.AUTHENTICATION_FAILED.getCode())
                        .message(ErrorCode.AUTHENTICATION_FAILED.getMessage())
                        .status(ErrorCode.AUTHENTICATION_FAILED.getStatus())
                        .build(),
                HttpStatus.UNAUTHORIZED);
    }

    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 경우
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("HandleAccessDeniedException", e);

        return new ResponseEntity<>(ErrorResponse.builder()
                .code(ErrorCode.ACCESS_DENIED.getCode())
                .message(e.getMessage())
                .status(ErrorCode.ACCESS_DENIED.getStatus())
                .build(),
                HttpStatus.FORBIDDEN);
    }

//    /**
//     * Jwt AccessToken 이 존재하지 않는 경우
//     * @param ex
//     * @return
//     */
//    @ExceptionHandler(NotHaveAccessTokenException.class)
//    protected ResponseEntity<?> notHaveAccessTokenException(NotHaveAccessTokenException ex) {
//        log.info("HandleNotHaveAccessTokenException", ex);
//        return new ResponseEntity<>(
//                ErrorResponse.builder()
//                        .code(ErrorCode.NOT_JWT_TOKEN.getCode())
//                        .message(ErrorCode.NOT_JWT_TOKEN.getMessage())
//                        .status(ErrorCode.NOT_JWT_TOKEN.getStatus())
//                        .build(),
//                HttpStatus.BAD_REQUEST);
//    }


    /**
     * BusinessException 에 관한 모든 에러가 발생 한 경우
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
        log.error("HandleBusinessException", e);

        final ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getStatus())
                .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * RuntimeException 에 관한 모든 에러가 발생 한 경우
     */
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse> handleException(RuntimeException e) {
        log.error("HandleRuntimeException", e);

        return new ResponseEntity<>(ErrorResponse.builder()
                .code("RUNTIME")
                .message("알 수 없는 런타임 에러입니다.")
                .status(000)
                .build(),
                HttpStatus.BAD_REQUEST);
    }
}
