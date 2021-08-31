package com.dsu.industry.domain.product.dto;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductSearchDtoTest {

    @Test
    @DisplayName("String -> Date 날짜 변환 테스트")
    public void stringToDate() {
        String checkIn = "2021.09.01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate first = LocalDate.parse(checkIn, formatter);

        System.out.println(first.minusDays(1));
    }

    @Test
    @DisplayName("Date 날짜 하루 전날 변경 테스트")
    public void dateMinusOneDay() {
        /* given */
        String date = "2021.09.01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        String minusOneDay = "2021.08.31";
        LocalDate minusDate = LocalDate.parse(minusOneDay, formatter);

        /* when */
        LocalDate changeDate = localDate.minusDays(1);

        /* then */
        assertThat(minusDate).isEqualTo(changeDate);
    }
}