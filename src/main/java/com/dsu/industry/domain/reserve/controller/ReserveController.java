package com.dsu.industry.domain.reserve.controller;

import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.product.exception.ProductNotFoundException;
import com.dsu.industry.domain.product.repository.ProductRepository;
import com.dsu.industry.domain.product.repository.query.ProductQueryCustomRepository;
import com.dsu.industry.domain.reserve.dto.ReserveDto;
import com.dsu.industry.domain.reserve.service.ReserveService;
import com.dsu.industry.domain.user.entity.User;
import com.dsu.industry.domain.user.exception.UserNotFoundException;
import com.dsu.industry.domain.user.repository.UserRepository;
import com.dsu.industry.global.common.CommonResponse;
import com.dsu.industry.global.security.CurrentUser;
import com.dsu.industry.global.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ReserveController {

    private final ReserveService reserveService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductQueryCustomRepository productQueryRepository;

    /**
     * 예약 요청
     */
    @PostMapping("/reserve")
    CommonResponse<ReserveDto.ReserveIdRes> reserve(
            @CurrentUser UserPrincipal userPrincipal,
            @RequestBody ReserveDto.ReserveReq dto) {

        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new UserNotFoundException());

        Product product = productRepository.findById(dto.getProduct_id())
                .orElseThrow(() -> new ProductNotFoundException());

        ReserveDto.ReserveIdRes idRes;
        if(dto.isCoupon_availability()) {
            idRes = reserveService.reserveWithCoupon(dto);
        } else {
            idRes = reserveService.reserve(ReserveDto.ReserveSaveReq.toEntity(user, product, dto));
        }

        return CommonResponse.<ReserveDto.ReserveIdRes>builder()
                .code("200")
                .message("ok")
                .data(idRes)
                .build();
    }

    /**
     * 예약 조회 (예약 번호를 통한)
     */
    @GetMapping("/reserve/{reserve_id}")
    CommonResponse<ReserveDto.ReserveInfoRes> reserve_select(
            @PathVariable Long reserve_id) {

            return CommonResponse.<ReserveDto.ReserveInfoRes>builder()
                    .code("200")
                    .message("ok")
                    .data(reserveService.reserve_select(reserve_id))
                    .build();
    }

    /**
     * 예약 리스트 조회 (로그인한 회원에 대해서만)
     */
    @GetMapping("/reserve")
    CommonResponse<List<ReserveDto.ReserveInfoRes>> reserve_selectList(
            @CurrentUser UserPrincipal userPrincipal) {

            User user = userRepository.findById(userPrincipal.getId())
                    .orElseThrow(() -> new UserNotFoundException());

            return CommonResponse.<List<ReserveDto.ReserveInfoRes>>builder()
                    .code("200")
                    .message("ok")
                    .data(reserveService.reserve_selectList(user))
                    .build();
    }
}
