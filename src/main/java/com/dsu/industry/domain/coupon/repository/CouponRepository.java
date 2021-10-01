package com.dsu.industry.domain.coupon.repository;

import com.dsu.industry.domain.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
