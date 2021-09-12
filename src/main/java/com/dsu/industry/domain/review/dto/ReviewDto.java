package com.dsu.industry.domain.review.dto;

import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.review.entity.Review;
import com.dsu.industry.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class ReviewDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewReq {
        private Long product_id;
        private Long review_score;
        private String review_title;
        private String review_content;
    }

    @Data
    @NoArgsConstructor
    public static class ReviewSaveReq {

        public static Review toEntity(User user, Product product, ReviewReq reviewReq) {
            return Review.builder()
                    .user(user)
                    .product(product)
                    .title(reviewReq.getReview_title())
                    .content(reviewReq.getReview_content())
                    .score(reviewReq.getReview_score())
                    .build();
        }
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewInfoRes {

        private String user_name;
        private Long review_score;
        private String review_content;
        private LocalDate review_createAt;

        public static ReviewDto.ReviewInfoRes toDto(Review review) {
            return ReviewInfoRes.builder()
                    .user_name(review.getUser().getName())
                    .review_score(review.getScore())
                    .review_content(review.getContent())
                    .review_createAt(LocalDate.from(review.getCreatedAt()))
                    .build();
        }
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewIdRes {
        private Long id;
    }
}
