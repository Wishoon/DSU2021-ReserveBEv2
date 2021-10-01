package com.dsu.industry.domain.product.controller;

import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.dto.SearchDto;
import com.dsu.industry.domain.product.dto.mapper.ProductMapper;
import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.product.exception.ProductNotFoundException;
import com.dsu.industry.domain.product.repository.ProductRepository;
import com.dsu.industry.domain.product.service.query.ProductQueryService;
import com.dsu.industry.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductSearchController {

    private final ProductQueryService productSearchService;
    private final ProductRepository productRepository;

//    @GetMapping("/search/{category}/{city}/{checkIn}/{checkOut}/{peopleCnt}")
    @GetMapping("/product/search")
    CommonResponse<List<ProductDto.ProductInfoRes>> product_searchList(
            @ModelAttribute SearchDto productSearchDto) {

            return CommonResponse.<List<ProductDto.ProductInfoRes>>builder()
                    .code("200")
                    .message("ok")
                    .data(productSearchService.product_searchList(
                            ProductMapper.productSearchReqToDto(productSearchDto)
                    ))
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

            ProductDto.ProductAvailableReq req = ProductMapper.productAndDateToDto(
                    product, LocalDate.now()
            );

            return CommonResponse.<List<ProductDto.ProductAvailableRes>>builder()
                    .code("200")
                    .message("ok")
                    .data(productSearchService.product_available_search(req))
                    .build();
    }
}
