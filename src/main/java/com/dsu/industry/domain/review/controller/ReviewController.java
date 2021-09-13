package com.dsu.industry.domain.review.controller;

import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.product.exception.ProductNotFoundException;
import com.dsu.industry.domain.product.repository.ProductRepository;
import com.dsu.industry.domain.review.dto.ReviewDto;
import com.dsu.industry.domain.review.service.ReviewService;
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
public class ReviewController {

    private final ReviewService reviewService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @PostMapping("/review")
    CommonResponse<ReviewDto.ReviewIdRes> review_save(
            @RequestBody ReviewDto.ReviewReq req,
            @CurrentUser UserPrincipal userPrincipal) {

        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new UserNotFoundException());

        Product product = productRepository.findById(req.getProduct_id())
                        .orElseThrow(() -> new ProductNotFoundException());

        return CommonResponse.<ReviewDto.ReviewIdRes>builder()
                .code("200")
                .message("ok")
                .data(reviewService.review_save(
                        ReviewDto.ReviewSaveReq.toEntity(user, product, req)))
                .build();
    }

    @GetMapping("/review/{product_id}")
    CommonResponse<List<ReviewDto.ReviewInfoRes>> review_selectList_ofProduct(
            @PathVariable Long product_id) {

        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new ProductNotFoundException());

        return CommonResponse.<List<ReviewDto.ReviewInfoRes>>builder()
                .code("200")
                .message("ok")
                .data(reviewService.review_selectList_ofProduct(product))
                .build();
    }
}
