package com.dsu.industry.domain.review.repository;

import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<List<Review>> findByProduct(Product product);
}
