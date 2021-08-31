package com.dsu.industry.domain.product.service;

import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.dto.ProductSearchDto;
import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.product.repository.query.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductSearchService {

    private final ProductQueryRepository productQueryRepository;

    /**
     * 유저가 상품의 카테고리, 도시, 체크인, 체크아웃으로 정보 다중 조회
     * @param dto
     * @return
     */
    public List<ProductDto.ProductInfoRes> product_searchList(ProductSearchDto.SearchReq dto) {

        List<Product> products = productQueryRepository.findProductByAvailable(
                dto.getCategory(), dto.getCity(), dto.getCheckIn(), dto.getCheckOut(), dto.getDateCnt());

        return products.stream()
                .map(ProductDto.ProductInfoRes::toDto)
                .collect(Collectors.toList());
    }
}
