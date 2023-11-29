package com.web.shopgym.controllers;

import com.web.shopgym.entities.*;
import com.web.shopgym.enums.EImageType;
import com.web.shopgym.enums.EStatus;
import com.web.shopgym.payloads.request.CreateProductDto;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.payloads.request.ProductDetailDto;
import com.web.shopgym.payloads.response.DataTableResponse;
import com.web.shopgym.services.CloudinaryService;
import com.web.shopgym.services.ImageService;
import com.web.shopgym.services.ProductDetailService;
import com.web.shopgym.services.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/webapi/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_WAREHOUSE_MANAGEMENT')")
    public DataTableResponse getAllBySearchCriteria(DataTableRequest dataTableRequest) {
        Page<Product> result = null;
        result = this.productService.getAllBySearchCriteria(dataTableRequest);

        return DataTableResponse.build().ok()
                .totalRows(result.getTotalElements())
                .items(result.get().toList());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createProduct(@RequestBody CreateProductDto dto) {

        Product productSave = new Product();
        productSave.setName(dto.getName());
        productSave.setPrice(dto.getPrice());
        productSave.setDescription(dto.getDescription());
        productSave.setBrand(Brand.builder().id(dto.getBrandId()).build());
        productSave.setCategory(Category.builder().id(dto.getCategoryId()).build());
        productSave.setStatus(EStatus.ACTIVE);

        Product product = this.productService.save(productSave);

        if(product == null) return new ResponseEntity<>("ERROR", HttpStatus.OK);

        dto.getProductDetails().forEach((productDetailDto) -> {
            ProductDetail productDetailSave = ProductDetail.builder()
                    .code(productDetailDto.getCode())
                    .quantity(productDetailDto.getQuantity())
                    .price(productDetailDto.getPrice())
                    .product(product)
                    .size(Size.builder().id(productDetailDto.getSizeId()).build())
                    .color(Color.builder().id(productDetailDto.getColorId()).build())
                    .material(Material.builder().id(productDetailDto.getMaterialId()).build())
                    .status(EStatus.ACTIVE).build();
            ProductDetail productDetail = this.productDetailService.save(productDetailSave);

            List<Image> images = new ArrayList<>();
            productDetailDto.getImageFiles().forEach((imageFile) -> {
                Map imageMap = this.cloudinaryService.upload(imageFile);
                Image image = Image.builder()
                        .url(String.valueOf(imageMap.get("url")))
                        .type(EImageType.PRODUCT_DETAIL)
                        .publicId(String.valueOf(imageMap.get("public_id")))
                        .secondaryId(productDetail.getId())
                        .build();
            });

            this.imageService.saveAll(images);
        });

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
