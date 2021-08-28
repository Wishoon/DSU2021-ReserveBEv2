package com.dsu.industry.domain.product.repository.query;

import com.dsu.industry.domain.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductQueryRepositoryTest {

    @Autowired
    ProductQueryRepository productQueryRepository;

    @Test
    @DisplayName("조건에 따른 상품 예약 가능 여부 확인")
    void productAvailable_list() {
        String category = "호텔";
        String city = "부산";
        String checkIn = "2021.08.23";
        String checkOut = "2021.08.24";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        LocalDate checkIn_c = LocalDate.parse(checkIn, formatter);
        LocalDate checkOut_c = LocalDate.parse(checkOut, formatter);

        List<Product> products = productQueryRepository.findProductByAvailable(category, city, checkIn_c, checkOut_c);

        // 상품1. 23 ~ 24, 상품2. 23
        assertThat(products.size()).isEqualTo(3);
    }
}