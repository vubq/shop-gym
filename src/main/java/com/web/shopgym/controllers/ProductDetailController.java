package com.web.shopgym.controllers;

import com.web.shopgym.entities.ProductDetail;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.payloads.response.DataTableResponse;
import com.web.shopgym.services.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/webapi/product-detail")
public class ProductDetailController {

    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_WAREHOUSE_MANAGEMENT')")
    public DataTableResponse getAllBySearchCriteria(DataTableRequest dataTableRequest) {
        Page<ProductDetail> result = null;
        result = this.productDetailService.getAllBySearchCriteria(dataTableRequest);

        return DataTableResponse.build().ok()
                .totalRows(result.getTotalElements())
                .items(result.get().toList());
    }
}
