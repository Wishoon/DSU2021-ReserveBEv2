package com.dsu.industry.domain.product.service;

import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.entity.Category;
import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.product.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Transactional
@SpringBootTest
class ProductServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;

    private ProductDto.ProductSaveReq productSaveReq;

    @BeforeEach
    public void before() {



        productSaveReq = ProductDto.ProductSaveReq.builder()
                .name("상품1")
                .sub_name("상품하위1")
                .category_id(3L)
                .price(10000L)
                .people_maxCnt(10L)
                .description("상품에 대한 설명입니다.")
                .build();
    }
    @Test
    void 등록상품_카테고리_검색() {
        /* given */

        /* when */
        Category category = categoryRepository.findById(productSaveReq.getCategory_id())
                .orElseThrow(() -> new IllegalStateException("추후 수정"));

        /* then */
        assertThat(category.getId()).isEqualTo(3L);
        assertThat(category.getName()).isEqualTo("호텔");
    }

    @Test
    void 상품DTO_상품Entity_변환() {
        /* given */
        Category category = categoryRepository.findById(productSaveReq.getCategory_id()).orElseThrow(() -> new IllegalStateException("하아"));

        /* when */
        Product product = ProductDto.ProductSaveReq.toEntity(productSaveReq, category);

        /* then */
        assertNotNull(product);
    }

    @Test
    void 상품등록() {
        /* given */
        Category category = categoryRepository.findById(productSaveReq.getCategory_id()).orElseThrow(() -> new IllegalStateException("하아"));
        Product product = ProductDto.ProductSaveReq.toEntity(productSaveReq, category);

        /* when */
        ProductDto.ProductIdRes res = productService.product_save(product);

        /* then */
        assertNotNull(res);
    }

    @Rollback(false)
    @Test
    @DisplayName("상품 수정")
    void product_revise() {
        /* given */
        Category save_category = categoryRepository.findById(productSaveReq.getCategory_id()).orElseThrow(() -> new IllegalStateException("하아"));
        Product save_product = ProductDto.ProductSaveReq.toEntity(productSaveReq, save_category);
        ProductDto.ProductIdRes save_res = productService.product_save(save_product);

        ProductDto.ProductSaveReq productReviseReq = ProductDto.ProductSaveReq.builder()
                .name("상품1")
                .sub_name("상품하위1")
                .category_id(3L)
                .price(10000L)
                .people_maxCnt(10L)
                .description("상품에 대한 설명을 수정했습니다.")
                .build();

        /* when */
        Category category = categoryRepository.findById(productReviseReq.getCategory_id()).orElseThrow(() -> new IllegalStateException("하아"));
        Product product = ProductDto.ProductSaveReq.toEntity(productReviseReq, category);

        ProductDto.ProductIdRes res = productService.product_revise(save_res.getId(), product);

        /* then */
        assertNotNull(res);
    }
}