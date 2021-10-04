package com.dsu.industry.domain.product.dto.mapper;

import com.dsu.industry.domain.product.dto.PhotoDto;
import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.dto.SearchDto;
import com.dsu.industry.domain.product.entity.Category;
import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.global.common.Address;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

public class ProductMapper {

    /**
     * 상품생성 DTO -> Entity
     * @param req
     * @param category
     * @return
     */
    public static Product productSaveDtoToEntity(ProductDto.ProductSaveReq req, Category category) {
        return Product.builder()
                .name(req.getName())
                .sub_name(req.getSub_name())
                .category(category)
                .address(new Address(req.getAddr1_depth_nm(),
                        req.getAddr2_depth_nm(),
                        req.getAddr3_depth_nm(),
                        req.getAddr4_depth_nm()))
                .price(req.getPrice())
                .people_maxCnt(req.getPeople_maxCnt())
                .description(req.getDescription())
                .build();
    }

    /**
     * 상품수정Dto -> Entity
     * @param req
     * @param category
     * @return
     */
    public static Product productReviseDtoToEntity(ProductDto.ProductReviseReq req, Category category) {
        return Product.builder()
                .name(req.getName())
                .sub_name(req.getSub_name())
                .category(category)
                .address(new Address(req.getAddr1_depth_nm(),
                        req.getAddr2_depth_nm(),
                        req.getAddr3_depth_nm(),
                        req.getAddr4_depth_nm()))
                .price(req.getPrice())
                .people_maxCnt(req.getPeople_maxCnt())
                .description(req.getDescription())
                .build();
    }

    /**
     *  dateCnt : checkIn ~ checkOut 숙박 기간 (0박 기준)
     *  checkOut : 0박 기준 검색을 위해 -1일 카운트
     */
    public static ProductDto.ProductSearchReq productSearchReqToDto(
            String category, String city, String checkIn, String checkOut, Long peopleCnt) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate first = LocalDate.parse(checkIn, formatter);
        LocalDate last = LocalDate.parse(checkOut, formatter);

        return ProductDto.ProductSearchReq.builder()
                .category(category)
                .city(city)
                .dateCnt(ChronoUnit.DAYS.between(first, last))
                .checkIn(first)
                .checkOut(last.minusDays(1))
                .peopleCnt(peopleCnt)
                .build();
    }

    public static ProductDto.ProductSearchReq productSearchReqToDto(
            SearchDto dto) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate first = LocalDate.parse(dto.getCheckIn(), formatter);
        LocalDate last = LocalDate.parse(dto.getCheckOut(), formatter);

        return ProductDto.ProductSearchReq.builder()
                .category(dto.getCategory())
                .city(dto.getCity())
                .dateCnt(ChronoUnit.DAYS.between(first, last))
                .checkIn(first)
                .checkOut(last.minusDays(1))
                .peopleCnt(dto.getPeopleCnt())
                .build();
    }

    /**
     *
     */
    public static ProductDto.ProductInfoRes productEntityToDto(Product product) {
        return ProductDto.ProductInfoRes.builder()
                .product_id(product.getId())
                .product_name(product.getName())
                .product_sub_name(product.getSub_name())
                .product_category_name(product.getCategory().getName())
                .address(product.getAddress())
                // 개발 마무리 후 생성자 말고 메서드로 변경
                .product_img(
                        product.getPhotoList().stream()
                                .map(photo -> new PhotoDto.PhotoInfoRes(photo))
                                .collect(Collectors.toList())
                )
                .product_price(product.getPrice())
                .product_people_maxCnt(product.getPeople_maxCnt())
                .product_description(product.getDescription())
                .build();
    }

    /**
     * 상품에 대한 날짜 가능 여부 요청 Dto (Product, TodayDate)
     */
    public static ProductDto.ProductAvailableReq productAndDateToDto(Product product, LocalDate today) {
        return ProductDto.ProductAvailableReq.builder()
                .product(product)
                .today(today)
                .build();
    }
}
