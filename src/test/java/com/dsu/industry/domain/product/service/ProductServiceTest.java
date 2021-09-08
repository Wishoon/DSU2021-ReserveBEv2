package com.dsu.industry.domain.product.service;

import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.entity.AvailableDate;
import com.dsu.industry.domain.product.entity.Category;
import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.product.exception.AvailableDateNotFoundException;
import com.dsu.industry.domain.product.repository.CategoryRepository;
import com.dsu.industry.domain.product.repository.query.ProductQueryRepository;
import com.dsu.industry.domain.reserve.entity.Reserve;
import com.dsu.industry.domain.reserve.exception.ReserveNotFoundException;
import com.dsu.industry.domain.reserve.repository.ReserveRepository;
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
    @Autowired
    private ProductQueryRepository productQueryRepository;
    @Autowired
    private ReserveRepository reserveRepository;

    private ProductDto.ProductReq productReq;

    @BeforeEach
    public void before() {

        productReq = ProductDto.ProductReq.builder()
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
        Category category = categoryRepository.findById(productReq.getCategory_id())
                .orElseThrow(() -> new IllegalStateException("추후 수정"));

        /* then */
        assertThat(category.getId()).isEqualTo(3L);
        assertThat(category.getName()).isEqualTo("호텔");
    }

    @Test
    void 상품DTO_상품Entity_변환() {
        /* given */
        Category category = categoryRepository.findById(productReq.getCategory_id()).orElseThrow(() -> new IllegalStateException("하아"));

        /* when */
        Product product = ProductDto.ProductSaveReq.toEntity(productReq, category);

        /* then */
        assertNotNull(product);
    }

    @Test
    void 상품등록() {
        /* given */
        Category category = categoryRepository.findById(productReq.getCategory_id()).orElseThrow(() -> new IllegalStateException("하아"));
        Product product = ProductDto.ProductSaveReq.toEntity(productReq, category);

        /* when */
        ProductDto.ProductIdRes id = productService.product_save(product);

        /* then */
        assertNotNull(id);
    }

    @Rollback(false)
    @Test
    @DisplayName("상품 수정")
    void product_revise() {
        /* given */
        Category save_category = categoryRepository.findById(productReq.getCategory_id()).orElseThrow(() -> new IllegalStateException("하아"));
        Product save_product = ProductDto.ProductSaveReq.toEntity(productReq, save_category);
        ProductDto.ProductIdRes id = productService.product_save(save_product);

        ProductDto.ProductReq productReviseReq = ProductDto.ProductReq.builder()
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

        ProductDto.ProductIdRes res = productService.product_revise(id.getId(), product);

        /* then */
        assertNotNull(res);
    }

//    @Test
//    @DisplayName("")
//    void product_to_available() {
//
//        Reserve reserve = reserveRepository.findById(8L)
//                .orElseThrow(() -> new ReserveNotFoundException());
//
//        Product product = productQueryRepository
//                .findProductByAvailable(reserve.getProduct(),
//                                        reserve.getCheckIn(),
//                                        reserve.getCheckOut().minusDays(1),
//                                        reserve.calculatingWithCheckInAndCheckOut(
//                                                reserve.getCheckIn(),
//                                                reserve.getCheckOut()
//                                        ))
//                .orElseThrow(() -> new AvailableDateNotFoundException());
//
//        List<AvailableDate> availableDates =
//    }
}