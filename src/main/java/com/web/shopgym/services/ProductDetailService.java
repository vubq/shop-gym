package com.web.shopgym.services;

import com.web.shopgym.entities.ProductDetail;
import com.web.shopgym.payloads.request.DataTableRequest;
import org.springframework.data.domain.Page;

public interface ProductDetailService {

    Page<ProductDetail> getAllBySearchCriteria(DataTableRequest dataTableRequest);

    ProductDetail save(ProductDetail productDetail);
}
