package com.dsu.industry.domain.user.dto;

import com.dsu.industry.domain.user.entity.Address;
import lombok.*;

public class UserDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserJoinReq {
        private String email;
        private String password;
        private String name;
        private String phone;
        private String addr1_depth_nm;
        private String addr2_depth_nm;
        private String addr3_depth_nm;
        private String addr4_depth_nm;
        private String authority;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserInfoRes {
        public String email;
        private String name;
        private String phone;
        private Address address;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserReviseReq {
        private String name;
        private String phone;
        private String addr1_depth_nm;
        private String addr2_depth_nm;
        private String addr3_depth_nm;
        private String addr4_depth_nm;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserIdRes {
        private Long id;
    }
}
