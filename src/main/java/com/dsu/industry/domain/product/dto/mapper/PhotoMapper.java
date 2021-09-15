package com.dsu.industry.domain.product.dto.mapper;

import com.dsu.industry.domain.product.dto.PhotoDto;
import com.dsu.industry.domain.product.entity.Photo;
import com.dsu.industry.domain.product.entity.PhotoType;
import com.dsu.industry.domain.product.entity.Product;

public class PhotoMapper {

    public static Photo photoSaveDtoToEntity(Product product, String img_url) {
        return Photo.builder()
                .product(product)
                .photoUrl(img_url)
                .photoType(PhotoType.MAIN)
                .build();
    }

    public static Photo photoReviseDtoToEntity(Product product, String img_url) {

        return Photo.builder()
                .product(product)
                .photoUrl(img_url)
                .photoType(PhotoType.MAIN)
                .build();
    }
}
