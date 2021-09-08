package com.dsu.industry.domain.product.dto;

import com.dsu.industry.domain.product.entity.AvailableDate;
import com.dsu.industry.domain.product.entity.Category;
import com.dsu.industry.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ProductDto {


    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductReq {
        private String name;
        private String sub_name;
        private Long category_id;
        private String img_url;
        private Long price;
        private Long people_maxCnt;
        private String description;

    }

    @Builder
    @Data
    @NoArgsConstructor
    public static class ProductSaveReq {

        public static Product toEntity(ProductReq dto, Category category) {
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
    public static class ProductInfoRes {
        private Long id;
        private String name;
        private String sub_name;

        private String category_name;
        // 주소

        // 이미지 URL
        private String img_url;
        // 평점

        // 댓글 갯수

        private Long price;
        private Long people_maxCnt;
        private String description;

        @Builder
        public static ProductInfoRes toDto(Product product) {
            return ProductInfoRes.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .sub_name(product.getSub_name())
                    .category_name(product.getCategory().getName())
                    .img_url(product.getPhotoList().get(0).getPhotoUrl())
                    .price(product.getPrice())
                    .people_maxCnt(product.getPeople_maxCnt())
                    .description(product.getDescription())
                    .build();
        }
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductSearchReq {
        private String category;
        private String city;
        private LocalDate checkIn;
        private LocalDate checkOut;
        private Long peopleCnt;
        private Long dateCnt;

        public static ProductDto.ProductSearchReq toDto(String category, String city, String checkIn, String checkOut, Long peopleCnt) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            LocalDate first = LocalDate.parse(checkIn, formatter);
            LocalDate last = LocalDate.parse(checkOut, formatter);

            return ProductDto.ProductSearchReq.builder()
                    .category(category)
                    .city(city)
                    .dateCnt(ChronoUnit.DAYS.between(first, last))
                    .checkIn(first)
                    .checkOut(last.minusDays(1))
                    .peopleCnt(peopleCnt)
                    .build();
        }
    }


    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductAvailableReq {

        private Product product;
        private LocalDate today;

        public static ProductDto.ProductAvailableReq toDto(Product product, LocalDate today) {

            return ProductAvailableReq.builder()
                    .product(product)
                    .today(today)
                    .build();
        }
    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductAvailableRes {
        private String date;
        private final boolean isAvailable = true;

        public static ProductDto.ProductAvailableRes toDto(AvailableDate entity) {

            return ProductDto.ProductAvailableRes.builder()
                    .date(entity.getDate().toString())
                    .build();
        }
    }

    @Data
    @Builder
    public static class ProductIdRes {
        private Long id;
    }

    @Data
    @Builder
    public static class ProductWithPhotoIdRes {
        private Long product_id;
        private Long photo_id;

        public static ProductWithPhotoIdRes create(
                ProductDto.ProductIdRes product_id,
                PhotoDto.PhotoIdRes photo_id) {
            return ProductWithPhotoIdRes.builder()
                    .product_id(product_id.getId())
                    .photo_id(photo_id.getId())
                    .build();
        }
    }
}
