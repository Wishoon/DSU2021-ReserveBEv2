package com.dsu.industry.domain.reserve.service;

import com.dsu.industry.domain.product.entity.*;
import com.dsu.industry.domain.product.repository.AvailableDateRepository;
import com.dsu.industry.domain.product.repository.ProductRepository;
import com.dsu.industry.domain.reserve.dto.ReserveDto;
import com.dsu.industry.domain.reserve.dto.mapper.ReserveMapper;
import com.dsu.industry.domain.reserve.entity.Reserve;
import com.dsu.industry.domain.reserve.entity.ReserveType;
import com.dsu.industry.domain.reserve.repository.ReserveRepository;
import com.dsu.industry.domain.user.entity.AuthProvider;
import com.dsu.industry.domain.user.entity.Authority;
import com.dsu.industry.domain.user.entity.User;
import com.dsu.industry.domain.user.repository.UserRepository;
import com.dsu.industry.global.common.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReserveServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    ReserveService reserveService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ReserveRepository reserveRepository;
    @Autowired
    AvailableDateRepository availableDateRepository;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    ReserveDto.ReserveReq dto;

    @BeforeEach
    public void before() {
        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now().plusDays(1);

        String str_checkIn = checkIn.format(format);
        String str_checkOut = checkOut.format(format);

        dto = ReserveDto.ReserveReq.builder()
                .product_id(2L)
                .checkIn(str_checkIn)
                .checkOut(str_checkOut)
                .people_cnt(4L)
                .total_payment(10000L)
                .sales_payment(4000L)
                .result_payment(6000L)
                .build();

        before_product();
    }

    @Rollback(value = false)
    @Test
    @DisplayName("예약 서비스 테스트")
    public void reserve() {
        /* given */
        User user = userRepository.findById(8L).get();
        Product product = productRepository.findById(2L).get();

        /* when */
        ReserveDto.ReserveIdRes reserve_id = reserveService
                .reserve(ReserveMapper.reserveSaveReqToEntity(user, product, dto));

        /* then */
        Reserve reserve = reserveRepository.findById(reserve_id.getId()).get();

        assertNotNull(reserve_id.getId());
        assertThat(reserve.getId()).isEqualTo(reserve_id.getId());
    }

    @Test
    @DisplayName("예약 번호를 통한 예약 내역 조회")
    public void reserve_select() {
        /* given */
        Long reserve_id = 9L;

        /* when */
        ReserveDto.ReserveInfoRes reserve = reserveService.reserve_select(reserve_id);

        /* then */
        assertNotNull(reserve);
        assertThat(reserve.getReserve_id()).isEqualTo(reserve_id);
        assertThat(reserve.getProduct_id()).isEqualTo(5L);
        assertThat(reserve.getProduct_name()).isEqualTo("상품2");
    }

    @Test
    @DisplayName("유저가 예약한 정보 리스트 조회")
    public void user_reserve_list() {
        /* given */
        User user = userRepository.findById(8L).get();

        /* when */
        List<ReserveDto.ReserveInfoRes> reserves = reserveService.reserve_selectList(user);

        /* then */
        assertNotNull(reserves);
        assertThat(reserves.get(0).getProduct_name()).isEqualTo("상품2");
    }

    public void before_product() {
        Category category = new Category();
        category.setName("호텔");
        em.persist(category);

        Address address = new Address("부산", "강서구", "명지동", "상세주소");

        Product product1 = new Product();
        product1.setName("상품1");
        product1.setSub_name("상품 부 이름1");
        product1.setPrice(100000L);
        product1.setPeople_maxCnt(10L);
        product1.setAddress(address);
        product1.setCategory(category);
        product1.setDescription("상품에 대한 설명");

        Photo photo1 = new Photo();
        photo1.setProduct(product1);
        photo1.setPhotoType(PhotoType.MAIN);
        photo1.setPhotoUrl("https://dsu-reserve-v2.s3.ap-northeast-2.amazonaws.com/static/busan.jpeg");
        product1.getPhotoList().add(photo1);

        AvailableDate date1 = new AvailableDate();
        date1.setProduct(product1);
        date1.setDate(LocalDate.now());
        product1.getAvailableDateList().add(date1);

        em.persist(product1);

        Product product2 = new Product();
        product2.setName("상품2");
        product2.setSub_name("상품 부 이름2");
        product2.setPrice(10000L);
        product2.setPeople_maxCnt(10L);
        product2.setAddress(address);
        product2.setCategory(category);
        product2.setDescription("상품에 대한 설명2");

        Photo photo2 = new Photo();
        photo2.setProduct(product1);
        photo2.setPhotoType(PhotoType.MAIN);
        photo2.setPhotoUrl("https://dsu-reserve-v2.s3.ap-northeast-2.amazonaws.com/static/busan.jpeg");
        product2.getPhotoList().add(photo2);

        AvailableDate date2 = new AvailableDate();
        date2.setProduct(product1);
        date2.setDate(LocalDate.now().plusDays(2));
        product2.getAvailableDateList().add(date2);

        em.persist(product2);

        User user = new User();
        user.setName("유저1");
        user.setEmail("test1@gmail.com");
        user.setPassword("$2a$10$8iZb1uwdcINJTw.oSL6QouuRz3azSmHBZvEWpSHrlxUSOW679jazO");
        user.setPhone("01012345678");
        user.setAddress(address);
        user.setAuthority(Authority.USER);
        user.setAuthProvider(AuthProvider.local);

        em.persist(user);

        Reserve reserve = new Reserve();
        reserve.setProduct(product2);
        reserve.setUser(user);
        reserve.setPeople_reserveCnt(4L);
        reserve.setTotal_price(10000L);
        reserve.setSales_price(4000L);
        reserve.setResult_price(6000L);
        reserve.setReserveType(ReserveType.CHECKIN_BEFORE);
        reserve.setCheckIn(LocalDate.now().plusDays(1));
        reserve.setCheckOut(LocalDate.now().plusDays(2));

        em.persist(reserve);
    }

}