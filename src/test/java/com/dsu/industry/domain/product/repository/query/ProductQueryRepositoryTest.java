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

    @BeforeEach
    public void before() {

        // 카테고리, 도시, 체크인, 체크아웃 확인 기본 데이터
        before_productAvailable_list();
    }

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

    public void before_productAvailable_list() {
        Category category = new Category();
        category.setName("호텔");
        em.persist(category);

        Address address = new Address("부산", "강서구", "명지동", "상세주소");

        Product product = new Product();
        product.setName("상품1");
        product.setSub_name("상품 부 이름1");
        product.setPrice(100000L);
        product.setPeople_maxCnt(10L);
        product.setAddress(address);
        product.setCategory(category);
        product.setDescription("상품에 대한 설명");

        Photo photo1 = new Photo();
        photo1.setProduct(product);
        photo1.setPhotoType(PhotoType.MAIN);
        photo1.setPhotoUrl("https://dsu-reserve-v2.s3.ap-northeast-2.amazonaws.com/static/busan.jpeg");

        AvailableDate date1 = new AvailableDate();
        date1.setProduct(product);
        date1.setDate(LocalDate.now());
        product.getAvailableDateList().add(date1);

        em.persist(product);
    }
}