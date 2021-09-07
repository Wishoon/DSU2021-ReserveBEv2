package com.dsu.industry.domain.product.controller;

import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.product.exception.ProductNotFoundException;
import com.dsu.industry.domain.product.repository.ProductRepository;
import com.dsu.industry.domain.product.service.ProductSearchService;
import com.dsu.industry.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductSearchController {

    private final ProductSearchService productSearchService;
    private final ProductRepository productRepository;

    @GetMapping("/search/{category}/{city}/{checkIn}/{checkOut}/{peopleCnt}")
    CommonResponse<List<ProductDto.ProductInfoRes>> product_searchList(
            @PathVariable String category,
            @PathVariable String city,
            @PathVariable String checkIn,
            @PathVariable String checkOut,
            @PathVariable Long peopleCnt) {

            ProductDto.ProductSearchReq req = ProductDto.ProductSearchReq.toDto(
                    category, city, checkIn, checkOut, peopleCnt
            );

            return CommonResponse.<List<ProductDto.ProductInfoRes>>builder()
                    .code("200")
                    .message("ok")
                    .data(productSearchService.product_searchList(req))
                    .build();
    }

    /**
     * 상품에 대해서 예약 가능한 날짜 리스트 전체를 조회하는 로직
     * @param product_id
     * @return
     */
    @GetMapping("/product/{product_id}/available")
    CommonResponse<List<ProductDto.ProductAvailableRes>> product_available_search (
            @PathVariable Long product_id) {

            Product product = productRepository.findById(product_id)
                        .orElseThrow(() -> new ProductNotFoundException());

            ProductDto.ProductAvailableReq req = ProductDto.ProductAvailableReq.toDto(
                    product, LocalDate.now()
            );

            return CommonResponse.<List<ProductDto.ProductAvailableRes>>builder()
                    .code("200")
                    .message("ok")
                    .data(productSearchService.product_available_search(req))
                    .build();
    }
}
