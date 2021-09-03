package com.dsu.industry.domain.user.controller;

import com.dsu.industry.domain.user.dto.AuthDto;
import com.dsu.industry.global.common.CommonResponse;
import com.dsu.industry.global.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    /**
     * 로그인
     */
    @PostMapping("/signIn")
    public CommonResponse<AuthDto.AuthRes> authenticationUser(@RequestBody AuthDto.SignInReq signInReq) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInReq.getEmail(),
                        signInReq.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);

        return CommonResponse.<AuthDto.AuthRes>builder()
                .code("200")
                .message("ok")
                .data(new AuthDto.AuthRes("Bearer", token))
                .build();
    }
}
