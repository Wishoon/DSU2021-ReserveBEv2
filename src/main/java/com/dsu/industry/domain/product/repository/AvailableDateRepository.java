package com.dsu.industry.domain.product.repository;

import com.dsu.industry.domain.product.entity.AvailableDate;
import com.dsu.industry.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AvailableDateRepository extends JpaRepository<AvailableDate, Long> {

    List<AvailableDate> findByProductAndDateGreaterThanEqual(Product product, LocalDate today);
}
