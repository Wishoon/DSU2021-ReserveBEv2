package com.dsu.industry.domain.product.controller;

import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.dto.ProductDto.ProductIdRes;
import com.dsu.industry.domain.product.entity.Category;
import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.product.repository.CategoryRepository;
import com.dsu.industry.domain.product.service.ProductService;
import com.dsu.industry.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/product/{product_id}")
    CommonResponse<ProductDto.ProductInfoRes> product_select(@PathVariable("product_id") Long idReq) {
        return CommonResponse.<ProductDto.ProductInfoRes>builder()
                .code("200")
                .message("ok")
                .data(productService.product_select(idReq))
                .build();
    }

    @GetMapping("/product/list")
    CommonResponse<List<ProductDto.ProductInfoRes>> product_selectList() {
        return CommonResponse.<List<ProductDto.ProductInfoRes>>builder()
                .code("200")
                .message("ok")
                .data(productService.product_selectList())
                .build();
    }

    @PostMapping("/product")
    CommonResponse<ProductIdRes> product_save(
            @RequestBody ProductDto.ProductReq dto) {

        Category category_save = categoryRepository.findById(dto.getCategory_id())
                .orElseThrow(() -> new IllegalStateException("추후 수정"));

        Product product = ProductDto.ProductSaveReq.toEntity(dto, category_save);
            
        return CommonResponse.<ProductIdRes>builder()
                .code("200")
                .message("ok")
                .data(productService.product_save(product))
                .build();
    }

    @PutMapping("/product/{product_id}")
    CommonResponse<ProductIdRes> product_revise(@PathVariable("product_id") Long idReq,
                                                @RequestBody ProductDto.ProductReq dto) {

        Category category_save = categoryRepository.findById(dto.getCategory_id())
                .orElseThrow(() -> new IllegalStateException(""));

        Product productRes = ProductDto.ProductSaveReq.toEntity(dto, category_save);

        return CommonResponse.<ProductIdRes>builder()
                .code("200")
                .message("ok")
                .data(productService.product_revise(idReq, productRes))
                .build();
    }
}
