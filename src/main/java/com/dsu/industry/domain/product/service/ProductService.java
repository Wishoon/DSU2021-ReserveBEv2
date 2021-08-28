package com.dsu.industry.domain.product.service;

import com.dsu.industry.domain.product.dto.ProductDto.*;
import com.dsu.industry.domain.product.entity.Category;
import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.product.repository.CategoryRepository;
import com.dsu.industry.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 유저가 상품을 선택했을 때 상품의 단일 정보 조회
     * @param idReq
     * @return
     */
    public ProductInfoRes product_select(Long idReq) {

        Product product_select = productRepository.findById(idReq)
                .orElseThrow(() -> new IllegalStateException("추후 수정"));

        ProductInfoRes productResDto = ProductInfoRes.toDto(product_select);
        return productResDto;
    }

    /**
     * 등록된 전체 상품의 정보 조회
     * @return
     */
    public List<ProductInfoRes> product_selectList() {

        List<Product> productList = productRepository.findAll();

        if(productList.size() == 0) {
            // 이후 에러 처리
        }
        return productList.stream()
                .map(ProductInfoRes::toDto).collect(Collectors.toList());
    }

    /**
     * 관리자가 상품을 등록
     * @param product
     * @return
     */
    // 사용자한테 받은 입력 JSON과 AWS에 저장한 이미지 파일의 경로를 파라미터로 받아야 함.
    public ProductIdRes product_save(Product product) {

        Product save = productRepository.save(product);
        return ProductIdRes.builder()
                .id(save.getId())
                .build();
    }

    /**
     * 관리자가 상품을 수정
     * @param idReq
     * @param productRes
     * @return
     */
    public ProductIdRes product_revise(Long idReq, Product productRes) {
        Product product = productRepository.findById(idReq)
                .orElseThrow(() -> new IllegalStateException("에러 발생"));

        product.update(productRes);

        return ProductIdRes.builder()
                .id(product.getId())
                .build();
    }

}
