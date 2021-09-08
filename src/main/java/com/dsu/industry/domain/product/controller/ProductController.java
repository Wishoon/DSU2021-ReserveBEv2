package com.dsu.industry.domain.product.controller;

import com.dsu.industry.domain.product.dto.PhotoDto;
import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.dto.ProductDto.ProductIdRes;
import com.dsu.industry.domain.product.entity.Category;
import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.product.repository.CategoryRepository;
import com.dsu.industry.domain.product.service.PhotoService;
import com.dsu.industry.domain.product.service.ProductService;
import com.dsu.industry.domain.product.service.s3.S3Service;
import com.dsu.industry.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    private final S3Service s3Service;
    private final PhotoService photoService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/product/{product_id}")
    CommonResponse<ProductDto.ProductInfoRes> product_select(@PathVariable("product_id") Long idReq) {
        return CommonResponse.<ProductDto.ProductInfoRes>builder()
                .code("200")
                .message("ok")
                .data(productService.product_select(idReq))
                .build();
    }

    @GetMapping("/product/list")
    CommonResponse<List<ProductDto.ProductInfoRes>> product_selectList() {
        return CommonResponse.<List<ProductDto.ProductInfoRes>>builder()
                .code("200")
                .message("ok")
                .data(productService.product_selectList())
                .build();
    }

    /**
     * 상품 저장 (상품 이미지를 String(URL)로 넘겨주는 경우)
     * @param dto
     * @return
     */
    @PostMapping("/product")
    CommonResponse<ProductDto.ProductIdRes> product_save(
            @RequestBody ProductDto.ProductReq dto) {

        Category category_save = categoryRepository.findById(dto.getCategory_id())
                .orElseThrow(() -> new IllegalStateException("추후 수정"));

        Product product = ProductDto.ProductSaveReq.toEntity(dto, category_save);
        ProductDto.ProductIdRes product_id = productService.product_save(product);

        // img_url이 null 일 경우 exception 처리 해줘야 함
        photoService.photo_save(PhotoDto.PhotoSaveReq.toEntity(product, dto.getImg_url()));

        return CommonResponse.<ProductDto.ProductIdRes>builder()
                .code("200")
                .message("ok")
                .data(product_id)
                .build();
    }

    /**
     * 상품 저장 (상품 이미지를 MultiPartFile로 넘겨주는 경우)
     * @param dto
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("/product/product-photo")
    CommonResponse<ProductDto.ProductWithPhotoIdRes> product_save(
            @RequestPart(value = "key") ProductDto.ProductReq dto,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile) throws IOException {

        Category category_save = categoryRepository.findById(dto.getCategory_id())
                .orElseThrow(() -> new IllegalStateException("추후 수정"));

        Product product = ProductDto.ProductSaveReq.toEntity(dto, category_save);
        ProductDto.ProductIdRes product_id = productService.product_save(product);

        // 사진 s3 로직 시작
        String product_img_url = s3Service.upload(multipartFile, "static");
        PhotoDto.PhotoIdRes photo_id = photoService.photo_save(PhotoDto.PhotoSaveReq.toEntity(product, product_img_url));

        return CommonResponse.<ProductDto.ProductWithPhotoIdRes>builder()
                .code("200")
                .message("ok")
                .data(ProductDto.ProductWithPhotoIdRes.create(product_id, photo_id))
                .build();
    }

    @PutMapping("/product/{product_id}")
    CommonResponse<ProductIdRes> product_revise(@PathVariable("product_id") Long idReq,
                                                @RequestBody ProductDto.ProductReq dto) {

        Category category_save = categoryRepository.findById(dto.getCategory_id())
                .orElseThrow(() -> new IllegalStateException(""));

        Product productRes = ProductDto.ProductSaveReq.toEntity(dto, category_save);

        return CommonResponse.<ProductIdRes>builder()
                .code("200")
                .message("ok")
                .data(productService.product_revise(idReq, productRes))
                .build();
    }
}
