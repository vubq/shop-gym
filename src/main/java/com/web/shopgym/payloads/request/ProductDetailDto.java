package com.web.shopgym.payloads.request;

import com.web.shopgym.entities.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ProductDetailDto {

    private String id;
    private String code;
    private Double price;
    private Integer quantity;
    private String productId;
    private String sizeId;
    private String colorId;
    private String materialId;

    private List<Image> images;
    private List<MultipartFile> imageFiles;
}
