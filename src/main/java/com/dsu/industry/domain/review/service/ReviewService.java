package com.dsu.industry.domain.review.service;

import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.review.dto.ReviewDto;
import com.dsu.industry.domain.review.entity.Review;
import com.dsu.industry.domain.review.exception.ReviewNotFoundException;
import com.dsu.industry.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * 리뷰 저장
     * @param review
     * @return
     */
    public ReviewDto.ReviewIdRes review_save(Review review) {

        Review review_save = reviewRepository.save(review);

        return ReviewDto.ReviewIdRes
                .builder()
                .id(review_save.getId())
                .build();
    }

    /**
     * 상품에 대한 리뷰 리스트 조회
     * @param product
     * @return
     */
    public List<ReviewDto.ReviewInfoRes> review_selectList_ofProduct(Product product) {

        List<Review> reviews = reviewRepository.findByProduct(product)
                .orElseThrow(() -> new ReviewNotFoundException());

        return reviews.stream()
                .map(ReviewDto.ReviewInfoRes::toDto)
                .collect(Collectors.toList());
    }
}
