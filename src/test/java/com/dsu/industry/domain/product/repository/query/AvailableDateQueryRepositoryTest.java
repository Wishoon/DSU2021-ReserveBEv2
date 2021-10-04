package com.dsu.industry.domain.product.repository.query;

import com.dsu.industry.domain.product.entity.*;
import com.dsu.industry.domain.product.repository.AvailableDateRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class AvailableDateQueryRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    AvailableDateRepository availableDateRepository;
    @Autowired
    AvailableDateQueryRepository availableDateQueryRepository;


    @Test
    @DisplayName("조회하는 상품에 대한 예약 가능 날짜 리스트 확인")
    public void findByAvailable() {
        /* given */
        Long product_id = 1L;
        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now();

        /* when */
        List<AvailableDate> availableDates = availableDateQueryRepository.findByAvailable(product_id, checkIn, checkOut);

        /* then */
        assertNotNull(availableDates);
    }

    @Test
    public void updateByAvailable() {
        /* given */
        Long product_id = 2L;
        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now();

        /* when */
        availableDateQueryRepository.updateByAvailable(product_id, checkIn, checkOut);

        /* then */
        Product product = productRepository.findById(product_id).get();
        List<AvailableDate> availableDates = availableDateRepository
                .findByProductAndDateGreaterThanEqual(product, checkIn);

        assertThat(availableDates.get(0).isTrue()).isEqualTo(false);
    }

}
