package com.dsu.industry.domain.product.controller;

import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.dto.ProductSearchDto;
import com.dsu.industry.domain.product.service.ProductSearchService;
import com.dsu.industry.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    @GetMapping("/search/{category}/{city}/{checkIn}/{checkOut}/{peopleCnt}")
    CommonResponse<List<ProductDto.ProductInfoRes>> product_searchList(
            @PathVariable String category,
            @PathVariable String city,
            @PathVariable String checkIn,
            @PathVariable String checkOut,
            @PathVariable Long peopleCnt) {

            ProductSearchDto.SearchReq req = ProductSearchDto.SearchReq.toDto(
                    category, city, checkIn, checkOut, peopleCnt
            );

            return CommonResponse.<List<ProductDto.ProductInfoRes>>builder()
                    .code("200")
                    .message("ok")
                    .data(productSearchService.product_searchList(req))
                    .build();
    }
}
