package com.dsu.industry.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProductSearchDto {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchReq {
        private String category;
        private String city;
        private LocalDate checkIn;
        private LocalDate checkOut;
        private Long peopleCnt;

        public static SearchReq toDto(String category, String city, String checkIn, String checkOut, Long peopleCnt) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

            return SearchReq.builder()
                    .category(category)
                    .city(city)
                    .checkIn(LocalDate.parse(checkIn, formatter))
                    .checkOut(LocalDate.parse(checkOut, formatter))
                    .peopleCnt(peopleCnt)
                    .build();
        }
    }
}
