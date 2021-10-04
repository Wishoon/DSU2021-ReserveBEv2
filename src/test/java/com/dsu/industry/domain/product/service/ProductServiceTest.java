package com.dsu.industry.domain.product.service;

import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.dto.mapper.ProductMapper;
import com.dsu.industry.domain.product.entity.*;
import com.dsu.industry.domain.product.exception.CategoryNotFoundException;
import com.dsu.industry.domain.product.repository.CategoryRepository;
import com.dsu.industry.global.common.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

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
    private ProductDto.ProductReviseReq productReviseReq;

    @BeforeEach
    public void before() {

        productSaveReq = ProductDto.ProductSaveReq.builder()
                .name("상품3")
                .sub_name("상품하위3")
                .category_id(1L)
                .img_url("https://dsu-reserve-img.s3.ap-northeast-2.amazonaws.com/static/85bf46dc-983b-4c16-9957-9f69b984d583img+3.jpeg")
                .price(30000L)
                .people_maxCnt(10L)
                .addr1_depth_nm("부산")
                .addr2_depth_nm("강서구")
                .addr3_depth_nm("명지동")
                .addr2_depth_nm("상세주소")
                .description("상품에 대한 설명입니다.")
                .build();

        productReviseReq = ProductDto.ProductReviseReq.builder()
                .name("상품수정3")
                .sub_name("상품수정하위3")
                .category_id(1L)
                .img_url("https://dsu-reserve-img.s3.ap-northeast-2.amazonaws.com/static/85bf46dc-983b-4c16-9957-9f69b984d583img+3.jpeg")
                .price(30000L)
                .people_maxCnt(10L)
                .addr1_depth_nm("부산")
                .addr2_depth_nm("강서구")
                .addr3_depth_nm("명지동")
                .addr2_depth_nm("상세주소")
                .description("상품에 대한 설명입니다.")
                .build();
    }

    @Test
    void 상품등록() {
        /* given */
        Category category = categoryRepository.findById(productSaveReq.getCategory_id())
                .orElseThrow(() -> new CategoryNotFoundException());
        Product product = ProductMapper.productSaveDtoToEntity(productSaveReq, category);

        /* when */
        ProductDto.ProductIdRes id = productService.product_save(product);

        /* then */
        assertNotNull(id);
        assertThat(id.getId()).isEqualTo(product.getId());
    }

    @Test
    @DisplayName("상품 수정")
    void product_revise() {
        /* given */
        // 상품 등록
        Category save_category = categoryRepository.findById(productSaveReq.getCategory_id())
                .orElseThrow(() -> new CategoryNotFoundException());
        Product save_product = ProductMapper.productSaveDtoToEntity(productSaveReq, save_category);
        ProductDto.ProductIdRes id = productService.product_save(save_product);

        // 수정할 상품 Entity
        Category category = categoryRepository.findById(productReviseReq.getCategory_id())
                .orElseThrow(() -> new CategoryNotFoundException());
        Product product = ProductMapper.productReviseDtoToEntity(productReviseReq, category);

        /* when */
        ProductDto.ProductIdRes res = productService.product_revise(id.getId(), product);

        /* then */
        assertNotNull(res);
    }

    @Test
    @DisplayName("상품 번호를 통한 검색")
    public void product_searchId() {
        /* given */
        Long product_id = 2L;

        /* when */
        ProductDto.ProductInfoRes res = productService.product_select(product_id);

        /* then */
        System.out.println(res.toString());
        assertNotNull(res);
        assertThat(res.getProduct_id()).isEqualTo(2L);
        assertThat(res.getProduct_name()).isEqualTo("상품2");
    }

    @Test
    @DisplayName("상품 전체 검색")
    public void product_searchAll() {
        /* given */

        /* when */
        List<ProductDto.ProductInfoRes> resList = productService.product_selectList();

        /* then */
        assertNotNull(resList);
        assertThat(resList.get(0).getProduct_id()).isEqualTo(1L);
        assertThat(resList.get(0).getProduct_name()).isEqualTo("상품1");
        assertThat(resList.get(0).getProduct_price()).isEqualTo(20000);
    }
}