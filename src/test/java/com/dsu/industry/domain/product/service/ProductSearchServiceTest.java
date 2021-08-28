package com.dsu.industry.domain.product.service;

import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.dto.ProductSearchDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductSearchServiceTest {

    @Autowired
    ProductSearchService productSearchService;


    @Test
    @DisplayName("조건에 맞는 상품 검색")
    public void product_searchList() {
        ProductSearchDto.SearchReq req = ProductSearchDto.SearchReq.toDto(
                "호텔", "부산", "2021.08.23", "2021.08.24", 5L
        );
        List<ProductDto.ProductInfoRes> productList = productSearchService.product_searchList(req);

        assertNotNull(productList);
        assertThat(productList.size()).isEqualTo("3");
    }

}