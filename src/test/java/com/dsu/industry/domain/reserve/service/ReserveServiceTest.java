package com.dsu.industry.domain.reserve.service;

import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.product.repository.AvailableDateRepository;
import com.dsu.industry.domain.product.repository.ProductRepository;
import com.dsu.industry.domain.reserve.dto.ReserveDto;
import com.dsu.industry.domain.reserve.entity.Reserve;
import com.dsu.industry.domain.reserve.repository.ReserveRepository;
import com.dsu.industry.domain.user.entity.User;
import com.dsu.industry.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReserveServiceTest {

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

    ReserveDto.ReserveReq dto;
    @BeforeEach
    public void before() {
        dto = ReserveDto.ReserveReq.builder()
                .product_id(2L)
                .checkIn("2021.09.10")
                .checkOut("2021.09.11")
                .people_cnt(4L)
                .total_payment(10000L)
                .sales_payment(4000L)
                .result_payment(6000L)
                .build();
    }

    @Test
    @DisplayName("예약 서비스 테스트")
    public void reserve() {
        /* given */
        User user = userRepository.findById(7L).get();
        Product product = productRepository.findById(2L).get();

        /* when */
        ReserveDto.ReserveIdRes reserve_id = reserveService.reserve(ReserveDto.ReserveSaveReq.toEntity(user, product, dto));

        /* then */
        Reserve reserve = reserveRepository.findById(reserve_id.getId()).get();

        assertNotNull(reserve_id.getId());
        assertThat(reserve.getId()).isEqualTo(reserve_id.getId());
    }

    @Test
    @DisplayName("예약 번호를 통한 예약 내역 조회")
    public void reserve_select() {
        /* given */
        Long reserve_id = 8L;

        /* when */
        ReserveDto.ReserveInfoRes reserve = reserveService.reserve_select(reserve_id);

        /* then */
        assertNotNull(reserve);
        assertThat(reserve.getReserve_id()).isEqualTo(reserve_id);
        assertThat(reserve.getProduct_id()).isEqualTo(2L);
        assertThat(reserve.getProduct_name()).isEqualTo("상품1");
    }

    @Test
    @DisplayName("유저가 예약한 정보 리스트 조회")
    public void user_reserve_list() {
        /* given */
        User user = userRepository.findById(7L).get();

        /* when */
        List<ReserveDto.ReserveInfoRes> reserves = reserveService.reserve_selectList(user);

        /* then */
        assertNotNull(reserves);
        assertThat(reserves.size()).isEqualTo(1);
        assertThat(reserves.get(0).getProduct_name()).isEqualTo("상품1");
    }

}