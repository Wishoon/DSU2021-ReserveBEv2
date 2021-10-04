package com.dsu.industry.domain.reserve.service;

import com.dsu.industry.domain.product.entity.*;
import com.dsu.industry.domain.product.repository.AvailableDateRepository;
import com.dsu.industry.domain.product.repository.ProductRepository;
import com.dsu.industry.domain.reserve.dto.ReserveDto;
import com.dsu.industry.domain.reserve.dto.mapper.ReserveMapper;
import com.dsu.industry.domain.reserve.entity.Reserve;
import com.dsu.industry.domain.reserve.entity.ReserveState;
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
                .product_id(1L)
                .reserve_checkIn(str_checkIn)
                .reserve_checkOut(str_checkOut)
                .reserve_peopleCnt(4L)
                .reserve_total_payment(10000L)
                .reserve_sales_payment(4000L)
                .reserve_result_payment(16000L)
                .build();

    }

    @Test
    @DisplayName("예약 서비스 테스트")
    public void reserve() {
        /* given */
        User user = userRepository.findById(1L).get();
        Product product = productRepository.findById(1L).get();

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
        Long reserve_id = 1L;

        /* when */
        ReserveDto.ReserveInfoRes reserve = reserveService.reserve_select(reserve_id);

        /* then */
        assertNotNull(reserve);
        assertThat(reserve.getReserve_id()).isEqualTo(reserve_id);
        assertThat(reserve.getProduct_id()).isEqualTo(2L);
        assertThat(reserve.getReserve_product_name()).isEqualTo("상품2");
    }

    @Test
    @DisplayName("유저가 예약한 정보 리스트 조회")
    public void user_reserve_list() {
        /* given */
        User user = userRepository.findById(1L).get();

        /* when */
        List<ReserveDto.ReserveInfoRes> reserves = reserveService.reserve_selectList(user);

        /* then */
        assertNotNull(reserves);
        assertThat(reserves.get(0).getReserve_product_name()).isEqualTo("상품2");
    }
}