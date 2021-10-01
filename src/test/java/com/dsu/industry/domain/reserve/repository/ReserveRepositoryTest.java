package com.dsu.industry.domain.reserve.repository;

import com.dsu.industry.domain.reserve.entity.Reserve;
import com.dsu.industry.domain.reserve.exception.ReserveNotFoundException;
import com.dsu.industry.domain.user.entity.User;
import com.dsu.industry.domain.user.exception.UserNotFoundException;
import com.dsu.industry.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReserveRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ReserveRepository reserveRepository;

    @Test
    @DisplayName("유저의 예약 리스트 조회")
    void reserve_list_to_user() {

        /* given */
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundException());

        /* when */
        List<Reserve> reserves = reserveRepository.findByUser(user)
                .orElseThrow(() -> new ReserveNotFoundException());

        /* then */
        assertNotNull(reserves);
        assertThat(reserves.get(0).getId()).isEqualTo(1);
    }

}