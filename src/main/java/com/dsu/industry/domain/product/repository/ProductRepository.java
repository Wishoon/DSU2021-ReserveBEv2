package com.dsu.industry.domain.product.repository;

import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.reserve.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
