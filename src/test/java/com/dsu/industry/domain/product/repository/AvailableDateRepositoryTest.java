package com.dsu.industry.domain.product.repository;

import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.entity.AvailableDate;
import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class AvailableDateRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    AvailableDateRepository availableDateRepository;

    @Test
    @DisplayName("상품의 금일 기준 예약 가능 리스트 조회 테스트")
    void productAvailable_toDate() {
        /* given */
        Product product = productRepository.findById(3L)
                .orElseThrow(() -> new ProductNotFoundException());

        ProductDto.ProductAvailableReq dto = ProductDto.ProductAvailableReq.toDto(
                product, LocalDate.now()
        );

        /* when */
       List<AvailableDate> dateList = availableDateRepository.findByProductAndDateGreaterThanEqual(
               dto.getProduct(), dto.getToday()
       );

        /* then */
        assertNotNull(dateList);
        assertThat(dateList.get(0).getDate()).isEqualTo("2021-09-10");
        assertThat(dateList.get(1).getDate()).isEqualTo("2021-09-11");
    }
}