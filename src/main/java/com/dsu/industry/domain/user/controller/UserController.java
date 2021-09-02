package com.dsu.industry.domain.user.controller;

import com.dsu.industry.domain.user.dto.UserDto;
import com.dsu.industry.domain.user.dto.mapper.UserMapper;
import com.dsu.industry.domain.user.service.UserService;
import com.dsu.industry.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
