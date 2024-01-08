package com.web.shopgym.controllers;

import com.web.shopgym.entities.*;
import com.web.shopgym.enums.EImageType;
import com.web.shopgym.enums.EStatus;
import com.web.shopgym.payloads.request.*;
import com.web.shopgym.payloads.response.DataTableResponse;
import com.web.shopgym.payloads.response.Response;
import com.web.shopgym.services.CloudinaryService;
import com.web.shopgym.services.ImageService;
import com.web.shopgym.services.ProductDetailService;
import com.web.shopgym.services.ProductService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("get-list-of-products-by-criteria")
    public DataTableResponse getListOfProductsByCriteria(DataTableRequest dataTableRequest, @RequestParam(value = "status") String status) {
        Page<Product> result = this.productService.getListOfProductsByCriteria(dataTableRequest, status);

        return DataTableResponse.build()
                .ok()
                .totalRows(result.getTotalElements())
                .items(result.get().toList());
    }

    @GetMapping("/{id}")
    public Response getById(@PathVariable("id") String id) {
        return Response.build().ok().data(this.productService.getById(id));
    }

    @PostMapping
    @Transactional
    public Response createProduct(@RequestBody ProductDto dto) {

        Product productSave = new Product();
        if(!Strings.isEmpty(dto.getId())) {
            productSave.setId(dto.getId());
        }
        productSave.setName(dto.getName());
        productSave.setPrice(dto.getPrice());
        productSave.setDescription(dto.getDescription());
        productSave.setBrand(Brand.builder().id(dto.getBrandId()).build());
        productSave.setCategory(Category.builder().id(dto.getCategoryId()).build());
        productSave.setStatus(dto.getStatus());
        productSave.setImage(dto.getImages().get(0).getUrl());
        productSave.setCreatedAt(new Date());

        Product product = this.productService.save(productSave);

        List<Image> images = new ArrayList<>();

        dto.getImages().forEach((imageDto) -> {
            if(Strings.isEmpty(imageDto.getId())) {
                Image imageSave = Image.builder()
                        .url(imageDto.getUrl())
                        .type(EImageType.PRODUCT)
                        .publicId(imageDto.getPublicId())
                        .secondaryId(product.getId())
                        .build();
                images.add(imageSave);
            }
        });

        if(product == null) return Response.build().code(Response.CODE_INTERNAL_ERROR);

        dto.getProductDetails().forEach((productDetailDto) -> {
            ProductDetail productDetailSave = ProductDetail.builder()
                    .code(productDetailDto.getCode())
                    .quantity(productDetailDto.getQuantity())
                    .price(productDetailDto.getPrice())
                    .product(product)
                    .size(Size.builder().id(productDetailDto.getSizeId()).build())
                    .color(Color.builder().id(productDetailDto.getColorId()).build())
                    .material(Material.builder().id(productDetailDto.getMaterialId()).build())
                    .createdAt(new Date())
                    .status(EStatus.ACTIVE).build();
            if(!Strings.isEmpty(productDetailDto.getId())) {
                productDetailSave.setId(productDetailDto.getId());
            }
            ProductDetail productDetail = this.productDetailService.save(productDetailSave);

//            for(ImageDto image: productDetailDto.getImages()) {
//                Image imageSave = Image.builder()
//                        .url(image.getUrl())
//                        .type(EImageType.PRODUCT_DETAIL)
//                        .publicId(image.getPublicId())
//                        .secondaryId(productDetail.getId())
//                        .build();
//                images.add(imageSave);
//            }
        });
        this.imageService.saveAll(images);

        List<Image> imageDeletes = new ArrayList<>();

        if(dto.getImageDeletes() != null) {
            dto.getImageDeletes().forEach((image) -> {
                this.cloudinaryService.deleteByPublicId(image.getPublicId());
                Image imageSave = Image.builder()
                        .id(image.getId())
                        .url(image.getUrl())
                        .type(image.getType())
                        .publicId(image.getPublicId())
                        .secondaryId(image.getSecondaryId())
                        .build();
                imageDeletes.add(imageSave);
            });
        }
        this.imageService.deleteAll(imageDeletes);

        return Response.build().ok();
    }

    @GetMapping("product-detail/{productId}")
    public ResponseEntity<ProductDto> getProductDetailByProductId(@PathVariable("productId") String productId) {

        ProductDto product = ProductDto.toDto(this.productService.getById(productId));
        product.setImages(this.imageService.getAllBySecondaryId(product.getId()).stream().map(ImageDto::toDto).collect(Collectors.toList()));
        product.setProductDetails(this.productDetailService.getAllByProductId(product.getId()).stream().map(ProductDetailDto::toDto).collect(Collectors.toList()));
        product.setSizes(this.productDetailService.getAllSizeIdByProductId(product.getId()));
        product.setColors(this.productDetailService.getAllColorIdByProductId(product.getId()));
        product.setMaterials((this.productDetailService.getAllMaterialIdByProductId(product.getId())));

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("product-in-of-stock")
    public Response getAllProductInOfStock() {
        List<String> listProductId = this.productService.getAllProductIdInOfStock();
        List<Product> products = new ArrayList<>();
        if(listProductId != null) {
            listProductId.forEach((productId) -> {
                products.add(this.productService.getById(productId));
            });
        }

        return Response.build().ok().data(products);
    }

    @GetMapping("get-list-of-products-by-criteria-web-shop")
    public DataTableResponse getListOfProductsByCriteriaWebShop(DataTableRequest dataTableRequest, @RequestBody ProductWebShopRequest productWebShopRequest) {
        Page<ProductDetail> result = this.productDetailService.getListOfProductDetailsByCriteriaWebShop(
                dataTableRequest,
                productWebShopRequest.getCategories(),
                productWebShopRequest.getSizes(),
                productWebShopRequest.getColors(),
                productWebShopRequest.getMaterials(),
                productWebShopRequest.getPriceApprox());

        return DataTableResponse.build()
                .ok()
                .totalRows(result.getTotalElements())
                .items(result.get().map(productDetail -> Product.builder()
                        .id(productDetail.getProduct().getId())
                        .name(productDetail.getProduct().getName())
                        .description(productDetail.getProduct().getDescription())
                        .price(productDetail.getProduct().getPrice())
                        .brand(productDetail.getProduct().getBrand())
                        .category(productDetail.getProduct().getCategory())
                        .image(productDetail.getProduct().getImage())
                        .createdAt(productDetail.getProduct().getCreatedAt())
                        .status(productDetail.getProduct().getStatus())
                        .build()).toList());
    }
}
