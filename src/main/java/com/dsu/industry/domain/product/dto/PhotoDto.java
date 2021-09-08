package com.dsu.industry.domain.product.dto;

import com.dsu.industry.domain.product.entity.Photo;
import com.dsu.industry.domain.product.entity.PhotoType;
import com.dsu.industry.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PhotoDto {

    @Builder
    @Data
    @NoArgsConstructor
    public static class PhotoSaveReq {

        public static Photo toEntity(Product product, String url) {

            return Photo.builder()
                    .product(product)
                    .photoUrl(url)
                    .photoType(PhotoType.MAIN)
                    .build();
        }
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhotoIdRes {
        private Long id;
    }
}
