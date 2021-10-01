package com.dsu.industry.domain.reserve.dto.mapper;

import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.reserve.dto.ReserveDto;
import com.dsu.industry.domain.reserve.entity.Reserve;
import com.dsu.industry.domain.reserve.entity.ReserveState;
import com.dsu.industry.domain.user.entity.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReserveMapper {

    public static Reserve reserveSaveReqToEntity(User user, Product product, ReserveDto.ReserveReq dto) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate first = LocalDate.parse(dto.getReserve_checkIn(), formatter);
        LocalDate last = LocalDate.parse(dto.getReserve_checkOut(), formatter);

        return Reserve.builder()
                .user(user)
                .product(product)
                .people_reserveCnt(dto.getReserve_peopleCnt())
                .total_price(dto.getReserve_total_payment())
                .sales_price(dto.getReserve_sales_payment())
                .result_price(dto.getReserve_result_payment())
                .reserveState(ReserveState.CHECKIN_BEFORE)
                .checkIn(first)
                .checkOut(last)
                .build();
    }

    public static ReserveDto.ReserveInfoRes reserveEntityToDto(Reserve reserve) {

        return ReserveDto.ReserveInfoRes.builder()
                .reserve_id(reserve.getId())
                .product_id(reserve.getProduct().getId())
                .reserve_product_name(reserve.getProduct().getName())
                .product_mainImg_url(reserve.getProduct().getPhotoList().get(0).getPhotoUrl())
                .reserve_checkIn(reserve.getCheckIn())
                .reserve_checkOut(reserve.getCheckOut())
                .reserve_peopleCnt(reserve.getPeople_reserveCnt())
                .reserve_result_price(reserve.getResult_price())
                .build();
    }
}
