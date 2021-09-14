package com.dsu.industry.domain.user.dto.mapper;

import com.dsu.industry.domain.user.dto.UserDto;
import com.dsu.industry.global.common.Address;
import com.dsu.industry.domain.user.entity.AuthProvider;
import com.dsu.industry.domain.user.entity.Authority;
import com.dsu.industry.domain.user.entity.User;

public class UserMapper {

    /**
     * 유저 회원가입 DTO -> Entity 변환
     * @param req
     * @return
     */
    public static User userJoinDtoToEntity(UserDto.UserJoinReq req) {

        User user = User.builder()
                .email(req.getEmail())
                .password(req.getPassword())
                .name(req.getName())
                .phone(req.getPhone())
                .address(new Address(req.getAddr1_depth_nm(),
                        req.getAddr2_depth_nm(),
                        req.getAddr3_depth_nm(),
                        req.getAddr4_depth_nm()))
                .providerId(null)
                .authProvider(AuthProvider.local)
                .build();

        if(req.getAuthority() == Authority.USER.getKey()) {
            user.changeAuthority(Authority.USER);
        } else {
            user.changeAuthority(Authority.GUEST);
        }
        return user;
    }

    /**
     * 유저 정보 변경 DTO -> Entity 변환
     * @param req
     * @return
     */
    public static User userReviseDtoToEntity(UserDto.UserReviseReq req) {
        return User.builder()
                .name(req.getName())
                .phone(req.getPhone())
                .address(new Address(req.getAddr1_depth_nm(),
                        req.getAddr2_depth_nm(),
                        req.getAddr3_depth_nm(),
                        req.getAddr4_depth_nm()))
                .build();
    }

    /**
     * 유저 Entity -> UserInfoDto로 변환
     * @param user
     * @return
     */
    public static UserDto.UserInfoRes userEntityToDto(User user) {
        return UserDto.UserInfoRes.builder()
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build();
    }
}
