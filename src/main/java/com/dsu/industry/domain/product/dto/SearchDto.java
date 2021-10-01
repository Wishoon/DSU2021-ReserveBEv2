package com.dsu.industry.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {

    private String category;
    private String city;
    private String checkIn;
    private String checkOut;
    private Long peopleCnt;
}
