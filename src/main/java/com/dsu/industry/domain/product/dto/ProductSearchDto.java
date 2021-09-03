package com.dsu.industry.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
        private Long dateCnt;

        public static SearchReq toDto(String category, String city, String checkIn, String checkOut, Long peopleCnt) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            LocalDate first = LocalDate.parse(checkIn, formatter);
            LocalDate last = LocalDate.parse(checkOut, formatter);

            return SearchReq.builder()
                    .category(category)
                    .city(city)
                    .dateCnt(ChronoUnit.DAYS.between(first, last))
                    .checkIn(first)
                    .checkOut(last.minusDays(1))
                    .peopleCnt(peopleCnt)
                    .build();
        }
    }
}
