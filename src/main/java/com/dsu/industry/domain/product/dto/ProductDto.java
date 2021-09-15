package com.dsu.industry.domain.product.dto;

import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.global.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class ProductDto {


    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductSaveReq {
        private String name;
        private String sub_name;
        private Long category_id;
        private String img_url;
        private Long price;
        private Long people_maxCnt;
        private String description;

        private String addr1_depth_nm;
        private String addr2_depth_nm;
        private String addr3_depth_nm;
        private String addr4_depth_nm;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductReviseReq {
        private String name;
        private String sub_name;
        private Long category_id;

        private Long img_id;
        private String img_url;

        private Long price;
        private Long people_maxCnt;
        private String description;

        private String addr1_depth_nm;
        private String addr2_depth_nm;
        private String addr3_depth_nm;
        private String addr4_depth_nm;
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
        private Address address;

        // 이미지 URL
        private List<PhotoDto.PhotoInfoRes> img;

        // 평점

        // 댓글 갯수

        private Long price;
        private Long people_maxCnt;
        private String description;
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
    }


    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductAvailableReq {
        private Product product;
        private LocalDate today;
    }

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductAvailableRes {
        private String date;
        private final boolean isAvailable = true;
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
    }
}
