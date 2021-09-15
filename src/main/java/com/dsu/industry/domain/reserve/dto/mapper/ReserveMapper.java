package com.dsu.industry.domain.reserve.dto.mapper;

import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.reserve.dto.ReserveDto;
import com.dsu.industry.domain.reserve.entity.Reserve;
import com.dsu.industry.domain.reserve.entity.ReserveType;
import com.dsu.industry.domain.user.entity.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReserveMapper {

    public static Reserve reserveSaveReqToEntity(User user, Product product, ReserveDto.ReserveReq dto) {

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

    public static ReserveDto.ReserveInfoRes reserveEntityToDto(Reserve reserve) {

        return ReserveDto.ReserveInfoRes.builder()
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
