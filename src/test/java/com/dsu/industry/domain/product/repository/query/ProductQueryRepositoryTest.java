package com.dsu.industry.domain.product.repository.query;

import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.dto.mapper.ProductMapper;
import com.dsu.industry.domain.product.entity.*;
import com.dsu.industry.domain.product.repository.ProductRepository;
import com.dsu.industry.global.common.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.stream;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class ProductQueryRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductQueryRepository productQueryRepository;

    @Test
    @DisplayName("카테고리, 도시, 체크인, 체크아웃 조건에 따른 상품 예약 가능 여부 확인")
    void productAvailable_list() {
        /* given */
        String category = "호텔";
        String city = "부산";
        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now().plusDays(1);
        Long count = 1L;

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String str_checkIn = checkIn.format(format);
        String str_checkOut = checkOut.format(format);

        /* when */
        ProductDto.ProductSearchReq dto = ProductMapper.productSearchReqToDto(category, city, str_checkIn, str_checkOut, count);
        List<Product> products = productQueryRepository
                .findProductByAvailableDateWithCategoryAndCity(dto);

        /* then */
        assertNotNull(products);
        assertThat(products.size()).isEqualTo(1);
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