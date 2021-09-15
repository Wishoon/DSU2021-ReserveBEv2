package com.dsu.industry.domain.product.dto.mapper;

import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.entity.AvailableDate;

public class AvailableMapper {

    public static ProductDto.ProductAvailableRes availableDateEntityToDto(AvailableDate availableDate) {
        return ProductDto.ProductAvailableRes.builder()
                .date(availableDate.getDate().toString())
                .build();
    }
}
