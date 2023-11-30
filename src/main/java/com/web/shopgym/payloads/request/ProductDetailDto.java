package com.web.shopgym.payloads.request;

import com.web.shopgym.entities.ProductDetail;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailDto {

    private String id;
    private String code;
    private Double price;
    private Integer quantity;
    private String productId;
    private String sizeId;
    private String colorId;
    private String materialId;
    private List<ImageDto> images;

    public static ProductDetailDto toDto(ProductDetail productDetail) {
        return ProductDetailDto.builder()
                .id(productDetail.getId())
                .code(productDetail.getCode())
                .price(productDetail.getPrice())
                .quantity(productDetail.getQuantity())
                .productId(productDetail.getProduct().getId())
                .sizeId(productDetail.getSize().getId())
                .colorId(productDetail.getColor().getId())
                .materialId(productDetail.getMaterial().getId())
                .build();
    }
}
