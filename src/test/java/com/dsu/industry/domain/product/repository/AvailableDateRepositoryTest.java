package com.dsu.industry.domain.product.repository;

import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.dto.mapper.ProductMapper;
import com.dsu.industry.domain.product.entity.*;
import com.dsu.industry.domain.product.exception.ProductNotFoundException;
import com.dsu.industry.global.common.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class AvailableDateRepositoryTest {

//    @BeforeEach
//    public void before() {
//        before_product();
//    }

    @Autowired
    EntityManager em;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    AvailableDateRepository availableDateRepository;

    @Test
    @DisplayName("한 상품에 대한 금일부터의 예약 가능 날짜 조회 테스트")
    void productAvailable_toDate() {
        /* given */
        Product product = productRepository.findById(1L)
                .orElseThrow(() -> new ProductNotFoundException());

        ProductDto.ProductAvailableReq dto = ProductMapper.productAndDateToDto(
                product, LocalDate.now()
        );

        /* when */
       List<AvailableDate> dateList = availableDateRepository.findByProductAndDateGreaterThanEqual(
               dto.getProduct(), dto.getToday()
       );

        /* then */
        assertNotNull(dateList);
        assertThat(dateList.get(0).getDate()).isEqualTo(LocalDate.now());
    }
}