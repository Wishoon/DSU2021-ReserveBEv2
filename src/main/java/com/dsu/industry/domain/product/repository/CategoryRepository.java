package com.dsu.industry.domain.product.repository;

import com.dsu.industry.domain.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
