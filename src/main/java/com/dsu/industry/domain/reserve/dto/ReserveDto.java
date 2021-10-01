package com.dsu.industry.domain.reserve.dto;

import lombok.*;

import java.time.LocalDate;

public class ReserveDto {

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReserveReq {

        // 상품 정보
        private Long product_id;
        private String reserve_checkIn;
        private String reserve_checkOut;
        private Long reserve_peopleCnt;

        // 결제 정보

        private String reserve_pay_type;
        private String reserve_card_type;

        // 쿠폰 정보
        private boolean coupon_availability;
        private Long coupon_id;

        // 금액 정보
        private Long reserve_total_payment;
        private Long reserve_sales_payment;
        private Long reserve_result_payment;
    }

//    @Data
//    public static class ReserveWithCouponSaveDto {
//        private User user;
//        private Product product;
//        private Coupon coupon;
//        private ReserveReq dto;
//    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReserveInfoRes {
        private Long reserve_id;
        private Long product_id;
        private String reserve_product_name;
        private String product_mainImg_url;
        private LocalDate reserve_checkIn;
        private LocalDate reserve_checkOut;
        private Long reserve_peopleCnt;
        private Long reserve_result_price;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ReserveIdRes {
        private Long id;
    }
}
