package com.web.shopgym.payloads.request;

import com.web.shopgym.entities.Image;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class CreateProductDto {

    private String id;
    private String name;
    private String description;
    private String image;
    private Double price;
    private String categoryId;
    private String brandId;
    private List<Image> images;
    private List<ProductDetailDto> productDetails;
}
