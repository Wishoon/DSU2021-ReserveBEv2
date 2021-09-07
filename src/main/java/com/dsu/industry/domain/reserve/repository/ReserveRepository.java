package com.dsu.industry.domain.reserve.repository;

import com.dsu.industry.domain.reserve.entity.Reserve;
import com.dsu.industry.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    Optional<List<Reserve>> findByUser(User user);
}
