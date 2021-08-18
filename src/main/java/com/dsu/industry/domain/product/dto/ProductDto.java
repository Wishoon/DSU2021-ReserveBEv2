package com.dsu.industry.domain.product.dto;

import com.dsu.industry.domain.product.entity.Category;
import com.dsu.industry.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ProductDto {


    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductSaveReq {
        private String name;
        private String sub_name;
        private Long category_id;
        private Long price;
        private Long people_maxCnt;
        private String description;

        public static Product toEntity(ProductSaveReq dto, Category category) {
            return Product.builder()
                    .name(dto.getName())
                    .sub_name(dto.getSub_name())
                    .category(category)
                    .price(dto.getPrice())
                    .people_maxCnt(dto.getPeople_maxCnt())
                    .description(dto.getDescription())
                    .build();
        }
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductSelectRes {
        private String name;
        private String sub_name;

        private String category_name;

        private Long price;
        private Long people_maxCnt;
        private String description;

        @Builder
        public static ProductSelectRes toDto(Product product) {
            return ProductSelectRes.builder()
                    .name(product.getName())
                    .sub_name(product.getSub_name())
                    .category_name(product.getCategory().getName())
                    .price(product.getPrice())
                    .people_maxCnt(product.getPeople_maxCnt())
                    .description(product.getDescription())
                    .build();
        }
    }





    @Data
    @Builder
    public static class ProductIdRes {
        private Long id;
    }

}
