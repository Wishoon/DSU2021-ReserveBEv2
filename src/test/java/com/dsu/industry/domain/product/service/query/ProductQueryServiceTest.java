package com.dsu.industry.domain.product.service.query;

import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.dto.mapper.ProductMapper;
import com.dsu.industry.domain.product.entity.*;
import com.dsu.industry.domain.product.exception.ProductNotFoundException;
import com.dsu.industry.domain.product.repository.ProductRepository;
import com.dsu.industry.global.common.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class ProductQueryServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    ProductQueryService productQueryService;
    @Autowired
    ProductRepository productRepository;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy.MM.dd");

//    @BeforeEach
//    public void before() {
//        before_product();
//    }

    @Test
    @DisplayName("예약 상품 조건 검색")
    public void product_searchList() {
        /* given */
        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(2);

        String str_checkIn = checkIn.format(format);
        String str_checkOut = checkOut.format(format);

        ProductDto.ProductSearchReq req = ProductMapper.productSearchReqToDto(
                "호텔", "부산", str_checkIn, str_checkOut, 1L
        );

        /* when */
        List<ProductDto.ProductInfoRes> product_searchList = productQueryService.product_searchList(req);

        /* then */
        assertNotNull(product_searchList);
        assertThat(product_searchList.get(0).getProduct_id()).isEqualTo(2L);
        assertThat(product_searchList.get(0).getProduct_name()).isEqualTo("상품2");
    }


    @Test
    @DisplayName("한 상품에 대한 예약 가능 날짜 조회")
    public void product_available_search() {
        /* given */
        Product product = productRepository.findById(1L)
                .orElseThrow(() -> new ProductNotFoundException());
        LocalDate today = LocalDate.now();
        ProductDto.ProductAvailableReq req = ProductMapper.productAndDateToDto(
                product, today);

        /* when */
        List<ProductDto.ProductAvailableRes> availableResList = productQueryService.product_available_search(req);

        /* then */
        assertNotNull(availableResList);
        assertThat(availableResList.get(0).getDate()).isEqualTo(today.toString());
    }

    public void before_product() {
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
