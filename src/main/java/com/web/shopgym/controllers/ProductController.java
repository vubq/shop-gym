package com.web.shopgym.controllers;

import com.web.shopgym.dtos.*;
import com.web.shopgym.entities.*;
import com.web.shopgym.enums.EImageType;
import com.web.shopgym.enums.EStatus;
import com.web.shopgym.payloads.request.*;
import com.web.shopgym.payloads.response.DataTableResponse;
import com.web.shopgym.payloads.response.Response;
import com.web.shopgym.services.*;
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

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private MaterialService materialService;

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

    @PostMapping("get-list-of-products-by-criteria-web-shop")
    public DataTableResponse getListOfProductsByCriteriaWebShop(DataTableRequest dataTableRequest, @RequestBody ProductWebShopRequest productWebShopRequest) {
        Page<ProductDetail> result = this.productDetailService.getListOfProductDetailsByCriteriaWebShop(
                dataTableRequest,
                productWebShopRequest.getCategories(),
                productWebShopRequest.getSizes(),
                productWebShopRequest.getColors(),
                productWebShopRequest.getMaterials(),
                productWebShopRequest.getBrands(),
                productWebShopRequest.getPriceApprox());

        return DataTableResponse.build()
                .ok()
                .totalRows(result.getTotalElements())
                .items(result.get().map(productDetail -> ProductWebShopDTO.builder()
                        .id(productDetail.getProduct().getId())
                        .name(productDetail.getProduct().getName())
                        .description(productDetail.getProduct().getDescription())
                        .price(productDetail.getProduct().getPrice())
                        .brand(productDetail.getProduct().getBrand())
                        .category(productDetail.getProduct().getCategory())
                        .image(productDetail.getProduct().getImage())
                        .createdAt(productDetail.getProduct().getCreatedAt())
                        .rate(this.feedbackService.getRateProduct(productDetail.getProduct().getId()))
                        .quantitySold(this.orderDetailService.getQuantitySold(productDetail.getProduct().getId()))
                        .status(productDetail.getProduct().getStatus())
                        .build()).toList());
    }

    @GetMapping("get-product-by-id-web-shop/{id}")
    public Response getProductByIdWebShop(@PathVariable(value = "id") String id) {
        Product product = this.productService.getById(id);
        List<String> listSizeId = this.sizeService.getAllByProductId(product.getId());
        List<SizeDTO> sizes = new ArrayList<>();
        if(listSizeId.size() > 0) {
            listSizeId.forEach(sizeId -> {
                SizeDTO size = SizeDTO.toDto(this.sizeService.findById(sizeId).get());
                size.setIsOutOfStock(this.sizeService.getProductInStock(product.getId(), size.getId()).size() > 0 ? false : true);
                sizes.add(size);
            });
        }
        List<String> listColorId = this.colorService.getAllByProductId(product.getId());
        List<ColorDTO> colors = new ArrayList<>();
        if(listColorId.size() > 0) {
            listColorId.forEach(colorId -> {
                ColorDTO color = ColorDTO.toDto(this.colorService.findById(colorId).get());
                color.setIsOutOfStock(this.colorService.getProductInStock(product.getId(), color.getId()).size() > 0 ? false : true);
                colors.add(color);
            });
        }
        List<String> listMaterialId = this.materialService.getAllByProductId(product.getId());
        List<MaterialDTO> materials = new ArrayList<>();
        if(listMaterialId.size() > 0) {
            listMaterialId.forEach(materialId -> {
                MaterialDTO material = MaterialDTO.toDto(this.materialService.findById(materialId).get());
                material.setIsOutOfStock(this.materialService.getProductInStock(product.getId(), material.getId()).size() > 0 ? false : true);
                materials.add(material);
            });
        }
        FilterProductAttributeDTO filter = FilterProductAttributeDTO.builder().productId(product.getId()).build();
        List<ProductDetail> productDetails = this.productDetailService.filterProductAttributes(filter);
        Integer totalProductsAvailable = 0;
        for (ProductDetail productDetail : productDetails) {
            totalProductsAvailable += productDetail.getQuantity();
        }
        return Response.build().ok().data(ProductWebShopDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .brand(product.getBrand())
                .category(product.getCategory())
                .image(product.getImage())
                .createdAt(product.getCreatedAt())
                .rate(this.feedbackService.getRateProduct(product.getId()))
                .quantitySold(this.orderDetailService.getQuantitySold(product.getId()))
                .status(product.getStatus())
                .quantityOfProductAvailable(this.productDetailService.getQuantityOfProductAvailable(product.getId()))
                .listImage(this.imageService.findAllBySecondaryIdAndType(product.getId(), EImageType.PRODUCT).stream().map(Image::getUrl).toList())
                .colors(colors)
                .sizes(sizes)
                .materials(materials)
                .totalProductsAvailable(totalProductsAvailable)
                .build());
    }
}
