package com.dsu.industry.domain.product.repository.query;

import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProductQueryRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductQueryRepository productQueryRepository;
    @Autowired
    ProductQueryCustomRepository productQueryCustomRepository;

    @BeforeEach
    public void before() {

    }

    @Test
    @DisplayName("카테고리, 도시, 체크인, 체크아웃 조건에 따른 상품 예약 가능 여부 확인")
    void productAvailable_list() {
        String category = "호텔";
        String city = "부산";
        String checkIn = "2021.08.23";
        String checkOut = "2021.08.24";
        Long count = 1L;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        LocalDate checkIn_c = LocalDate.parse(checkIn, formatter);
        LocalDate checkOut_c = LocalDate.parse(checkOut, formatter);

        List<Product> products = productQueryRepository.findProductByAvailableWithCategoryAndCity
                (category, city, checkIn_c, checkOut_c, count);

        // 상품1. 23 ~ 24, 상품2. 23
        assertThat(products.size()).isEqualTo(3);
    }




//    @Test
//    @DisplayName("체크인, 체크아웃 조건에 따른 상품 예약 가능 여부 확인")
//    void productAvailable_isTrue() {
//        /* given */
//        String checkIn = "2021.09.10";
//        String checkOut = "2021.09.11";
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
//        LocalDate first = LocalDate.parse(checkIn, formatter);
//        LocalDate last = LocalDate.parse(checkOut, formatter);
//
//        Product product_select = productRepository.findById(3L)
//                .orElseThrow(() -> new ProductNotFoundException());
//
//        /* when */
//
//        Product product = productQueryRepository.findProductByAvailable(
//                product_select, first, last.minusDays(1), ChronoUnit.DAYS.between(first, last))
//                .orElseThrow(() -> new AvailableDateNotFoundException());
//
//        /* then */
//
//        assertNotNull(product);
//        assertThat(product.getId()).isEqualTo(product_select.getId());
//    }
}