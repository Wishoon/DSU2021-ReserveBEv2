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

    public ProductSelectRes product_select(Long idReq) {

        Product product_select = productRepository.findById(idReq)
                .orElseThrow(() -> new IllegalStateException("추후 수정"));

        ProductSelectRes productResDto = ProductSelectRes.toDto(product_select);
        return productResDto;
    }

    public List<ProductSelectRes> product_selectList() {

        List<Product> productList = productRepository.findAll();

        if(productList.size() == 0) {
            // 이후 에러 처리
        }
        return productList.stream()
                .map(ProductSelectRes::toDto).collect(Collectors.toList());
    }

    // 사용자한테 받은 입력 JSON과 AWS에 저장한 이미지 파일의 경로를 파라미터로 받아야 함.
    public ProductIdRes product_save(Product product) {

        Product save = productRepository.save(product);
        return ProductIdRes.builder()
                .id(save.getId())
                .build();
    }


    public ProductIdRes product_revise(Long idReq, Product productRes) {
        Product product = productRepository.findById(idReq)
                .orElseThrow(() -> new IllegalStateException("에러 발생"));

        product.update(productRes);

        return ProductIdRes.builder()
                .id(product.getId())
                .build();
    }

}
