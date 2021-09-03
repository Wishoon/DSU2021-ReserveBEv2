package com.dsu.industry.domain.user.controller;

import com.dsu.industry.domain.user.dto.UserDto;
import com.dsu.industry.domain.user.dto.mapper.UserMapper;
import com.dsu.industry.domain.user.service.UserService;
import com.dsu.industry.global.common.CommonResponse;
import com.dsu.industry.global.security.CurrentUser;
import com.dsu.industry.global.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;
    /**
     * 일반 회원 가입
     */
    @PostMapping("/user")
    public CommonResponse<UserDto.UserIdRes> userJoin(
            @RequestBody UserDto.UserJoinReq dto) {

        return CommonResponse.<UserDto.UserIdRes>builder()
                .code("200")
                .message("ok")
                .data(userService.joinUser(userMapper.userJoinDtoToEntity(dto)))
                .build();
    }

    /**
     * 유저 정보 확인
     * @param userPrincipal
     * @return
     */
    @GetMapping("/user")
    public CommonResponse<UserDto.UserInfoRes> user_select(
            @CurrentUser UserPrincipal userPrincipal) {

        return CommonResponse.<UserDto.UserInfoRes>builder()
                .code("200")
                .message("ok")
                .data(userService.user_select(userPrincipal.getId()))
                .build();
    }

    public CommonResponse<UserDto.UserIdRes> user_update(
            @CurrentUser UserPrincipal userPrincipal,
            @RequestBody UserDto.UserInfoRes userInfoRes) {

        return CommonResponse.<UserDto.UserIdRes>builder()
                .code("200")
                .message("ok")
                .data(userService.user_update(userPrincipal.getId(), userInfoRes))
                .build();
    }


}
