package com.web.shopgym.searchForm;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailSearchForm {

    private List<String> listProductDetailId;
}
