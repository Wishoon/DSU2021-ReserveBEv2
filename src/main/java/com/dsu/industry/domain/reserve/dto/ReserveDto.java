package com.dsu.industry.domain.reserve.dto;

import com.dsu.industry.domain.coupon.entity.Coupon;
import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.reserve.entity.Reserve;
import com.dsu.industry.domain.reserve.entity.ReserveType;
import com.dsu.industry.domain.user.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReserveDto {

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReserveReq {

        // 상품 정보
        private Long product_id;
        private String checkIn;
        private String checkOut;
        private Long people_cnt;

        // 결제 정보
        private String pay_type;
        private String card_type;

        // 쿠폰 정보
        private boolean coupon_availability;
        private Long coupon_id;

        // 금액 정보
        private Long total_payment;
        private Long sales_payment;
        private Long result_payment;
    }

    @Data
    public static class ReserveWithCouponSaveDto {
        private User user;
        private Product product;
        private Coupon coupon;
        private ReserveReq dto;

    }

    @Data
    public static class ReserveSaveReq {

        public static Reserve toEntity(User user, Product product, ReserveReq dto) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            LocalDate first = LocalDate.parse(dto.getCheckIn(), formatter);
            LocalDate last = LocalDate.parse(dto.getCheckOut(), formatter);

            return Reserve.builder()
                    .user(user)
                    .product(product)
                    .people_reserveCnt(dto.getPeople_cnt())
                    .total_price(dto.getTotal_payment())
                    .sales_price(dto.getSales_payment())
                    .result_price(dto.getResult_payment())
                    .reserveType(ReserveType.CHECKIN_BEFORE)
                    .checkIn(first)
                    .checkOut(last)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReserveInfoRes {
        private Long reserve_id;
        private Long product_id;
        private String product_name;
        private String product_img_url;
        private LocalDate checkIn;
        private LocalDate checkOut;
        private Long people_cnt;
        private Long result_price;

        public static ReserveInfoRes toDto(Reserve reserve) {

            return ReserveInfoRes.builder()
                    .reserve_id(reserve.getId())
                    .product_id(reserve.getProduct().getId())
                    .product_name(reserve.getProduct().getName())
                    .checkIn(reserve.getCheckIn())
                    .checkOut(reserve.getCheckOut())
                    .people_cnt(reserve.getPeople_reserveCnt())
                    .result_price(reserve.getResult_price())
                    .build();
        }
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ReserveIdRes {
        private Long id;
    }
}
