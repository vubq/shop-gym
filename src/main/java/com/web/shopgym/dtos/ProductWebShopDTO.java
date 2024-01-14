package com.web.shopgym.dtos;

import com.web.shopgym.entities.Brand;
import com.web.shopgym.entities.Category;
import com.web.shopgym.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductWebShopDTO {

    private String id;
    private String name;
    private String description;
    private String image;
    private Double price;
    private Category category;
    private Brand brand;
    private Date createdAt;
    private Double rate;
    private Integer quantitySold;
    private EStatus status;
    private Integer quantityOfProductAvailable;
    private List<String> listImage;
    private List<SizeDTO> sizes;
    private List<ColorDTO> colors;
    private List<MaterialDTO> materials;
    private Integer totalProductsAvailable;
}
