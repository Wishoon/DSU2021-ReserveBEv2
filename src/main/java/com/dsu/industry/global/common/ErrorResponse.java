package com.dsu.industry.global.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
    private int status;
}
