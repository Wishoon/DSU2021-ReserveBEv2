package com.dsu.industry.domain.product.service.query;

import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.dto.mapper.AvailableMapper;
import com.dsu.industry.domain.product.dto.mapper.ProductMapper;
import com.dsu.industry.domain.product.entity.AvailableDate;
import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.product.repository.AvailableDateRepository;
import com.dsu.industry.domain.product.repository.query.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductQueryService {

    private final ProductQueryRepository productQueryRepository;
    private final AvailableDateRepository availableDateRepository;

    /**
     * 유저가 상품의 카테고리, 도시, 체크인, 체크아웃으로 정보 다중 조회
     * @param req
     * @return
     */
    public List<ProductDto.ProductInfoRes> product_searchList(ProductDto.ProductSearchReq req) {

        List<Product> products = productQueryRepository.findProductByAvailableDateWithCategoryAndCity(req);

        return products.stream()
                .map(ProductMapper::productEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * 상품에 대하여 오늘 날짜 기준에 대한 예약 가능 리스트 조회
     * @param req
     * @return
     */
    public List<ProductDto.ProductAvailableRes> product_available_search(ProductDto.ProductAvailableReq req) {

        List<AvailableDate> availableDates = availableDateRepository.findByProductAndDateGreaterThanEqual(
                req.getProduct(), req.getToday()
        );

        return availableDates.stream()
                .map(AvailableMapper::availableDateEntityToDto)
                .collect(Collectors.toList());
    }
}
