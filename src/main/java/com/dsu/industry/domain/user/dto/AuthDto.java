package com.dsu.industry.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class AuthDto {

    @Getter
    public static class SignInReq {
        private String email;
        private String password;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthRes {
        private String type;
        private String accessToken;
    }

}
