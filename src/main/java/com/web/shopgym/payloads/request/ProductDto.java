package com.web.shopgym.payloads.request;

import com.web.shopgym.entities.Product;
import com.web.shopgym.enums.EStatus;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private String id;
    private String name;
    private String description;
    private String image;
    private Double price;
    private String categoryId;
    private String brandId;
    private List<ImageDto> images;
    private List<ProductDetailDto> productDetails;

    private List<String> sizes;
    private List<String> colors;
    private List<String> materials;

    private List<ImageDto> imageDeletes;

    private EStatus status;

    public static ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .brandId((product.getBrand().getId()))
                .status(product.getStatus())
                .build();
    }
}
