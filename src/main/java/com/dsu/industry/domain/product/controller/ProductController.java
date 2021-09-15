package com.dsu.industry.domain.product.controller;

import com.dsu.industry.domain.product.dto.PhotoDto;
import com.dsu.industry.domain.product.dto.ProductDto;
import com.dsu.industry.domain.product.dto.mapper.PhotoMapper;
import com.dsu.industry.domain.product.dto.mapper.ProductMapper;
import com.dsu.industry.domain.product.entity.Category;
import com.dsu.industry.domain.product.entity.Photo;
import com.dsu.industry.domain.product.entity.Product;
import com.dsu.industry.domain.product.exception.CategoryNotFoundException;
import com.dsu.industry.domain.product.repository.CategoryRepository;
import com.dsu.industry.domain.product.repository.PhotoRepository;
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
    private final PhotoRepository photoRepository;

    /**
     * 상품 id를 통한 상품 내용 조회
     * @param idReq
     * @return
     */
    @GetMapping("/product/{product_id}")
    CommonResponse<ProductDto.ProductInfoRes> product_select(@PathVariable("product_id") Long idReq) {
        return CommonResponse.<ProductDto.ProductInfoRes>builder()
                .code("200")
                .message("ok")
                .data(productService.product_select(idReq))
                .build();
    }

    /**
     * 등록된 상품 전체 조회
     * @return
     */
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
            @RequestBody ProductDto.ProductSaveReq dto) {

        Category category_save = categoryRepository.findById(dto.getCategory_id())
                .orElseThrow(() -> new CategoryNotFoundException());

        Product product = ProductMapper.productSaveDtoToEntity(dto, category_save);
        ProductDto.ProductIdRes product_id = productService.product_save(product);

        // img_url이 null 일 경우 분기 처
        photoService.photo_save(PhotoMapper.photoSaveDtoToEntity(product, dto.getImg_url()));

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
            @RequestPart(value = "key") ProductDto.ProductSaveReq dto,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile) throws IOException {

        Category category_save = categoryRepository.findById(dto.getCategory_id())
                .orElseThrow(() -> new CategoryNotFoundException());

        Product product = ProductMapper.productSaveDtoToEntity(dto, category_save);
        ProductDto.ProductIdRes product_id = productService.product_save(product);

        // 사진 s3 로직 시작
        String product_img_url = s3Service.upload(multipartFile, "static");
        PhotoDto.PhotoIdRes photo_id = photoService.photo_save(
                PhotoMapper.photoSaveDtoToEntity(product, product_img_url));

        return CommonResponse.<ProductDto.ProductWithPhotoIdRes>builder()
                .code("200")
                .message("ok")
                .data(ProductDto.ProductWithPhotoIdRes.builder()
                        .product_id(product_id.getId())
                        .photo_id(photo_id.getId())
                        .build())
                .build();
    }

    /**
     * 상품 수정
     * @param idReq
     * @param dto
     * @return
     */
    @PutMapping("/product/{product_id}")
    CommonResponse<ProductDto.ProductWithPhotoIdRes> product_revise(
            @PathVariable("product_id") Long idReq,
            @RequestBody ProductDto.ProductReviseReq dto) {

        Category findCategory = categoryRepository.findById(dto.getCategory_id())
                .orElseThrow(() -> new CategoryNotFoundException());

        // 상품 수정
        Product product = ProductMapper.productReviseDtoToEntity(dto, findCategory);
        ProductDto.ProductIdRes product_id = productService.product_revise(idReq, product);

        // 사진 수정
        Photo photo = PhotoMapper.photoReviseDtoToEntity(product, dto.getImg_url());
        PhotoDto.PhotoIdRes photo_id = photoService.photo_revise(dto.getImg_id(), photo);

        return CommonResponse.<ProductDto.ProductWithPhotoIdRes>builder()
                .code("200")
                .message("ok")
                .data(ProductDto.ProductWithPhotoIdRes.builder()
                        .product_id(product_id.getId())
                        .photo_id(photo_id.getId())
                        .build())
                .build();
    }
}
