package com.dsu.industry.domain.user.dto;

import com.dsu.industry.domain.user.entity.Address;
import com.dsu.industry.domain.user.entity.User;
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
        private Address address;
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

        public static UserInfoRes toDto(User user) {

            return UserInfoRes.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .phone(user.getPhone())
                    .address(user.getAddress())
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserInfoReq {
        private String name;
        private String phone;
        private Address address;

        public static User toEntity(UserInfoReq dto) {
            return User.builder()
                    .name(dto.getName())
                    .phone(dto.getPhone())
                    .address(dto.getAddress())
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserIdRes {
        private Long id;
    }
}
