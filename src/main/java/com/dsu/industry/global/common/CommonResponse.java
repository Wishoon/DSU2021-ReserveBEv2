package com.dsu.industry.global.common;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommonResponse<T> {
    private String code;
    private String message;
    private T data;
}
