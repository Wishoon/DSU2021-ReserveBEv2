package com.dsu.industry.domain.product.repository;

import com.dsu.industry.domain.product.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
