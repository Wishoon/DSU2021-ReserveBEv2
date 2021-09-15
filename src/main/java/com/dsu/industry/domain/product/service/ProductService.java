package com.dsu.industry.domain.product.service;

import com.dsu.industry.domain.product.dto.ProductDto.*;
import com.dsu.industry.domain.product.dto.mapper.ProductMapper;
import com.dsu.industry.domain.product.entity.Category;
import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.product.exception.ProductNotFoundException;
import com.dsu.industry.domain.product.repository.CategoryRepository;
import com.dsu.industry.domain.product.repository.ProductRepository;
import com.dsu.industry.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 유저가 상품을 선택했을 때 상품의 단일 정보 조회
     * @param idReq
     * @return
     */
    public ProductInfoRes product_select(Long idReq) {

        Product findProduct = productRepository.findById(idReq)
                .orElseThrow(() -> new ProductNotFoundException());

        ProductInfoRes productResDto = ProductMapper.productEntityToDto(findProduct);
        return productResDto;
    }

    /**
     * 등록된 전체 상품의 정보 조회
     * @return
     */
    public List<ProductInfoRes> product_selectList() {

       List<Product> productList = productRepository.findAll();

        if(productList.size() == 0) {
            new ProductNotFoundException();
        }
        return productList.stream()
                .map(ProductMapper::productEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * 관리자가 상품을 등록
     * @param product
     * @return
     */
    // 사용자한테 받은 입력 JSON과 AWS에 저장한 이미지 파일의 경로를 파라미터로 받아야 함.
    public ProductIdRes product_save(Product product) {

        Product saveProduct = productRepository.save(product);
        return ProductIdRes.builder()
                .id(saveProduct.getId())
                .build();
    }

    /**
     * 관리자가 상품을 수정
     * @param idReq
     * @param product
     * @return
     */
    public ProductIdRes product_revise(Long idReq, Product product) {
        Product findProduct = productRepository.findById(idReq)
                .orElseThrow(() -> new IllegalStateException("에러 발생"));

        findProduct.update(product);

        return ProductIdRes.builder()
                .id(findProduct.getId())
                .build();
    }

}
