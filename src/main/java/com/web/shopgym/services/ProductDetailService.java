package com.web.shopgym.services;

import com.web.shopgym.entities.ProductDetail;
import com.web.shopgym.payloads.request.DataTableRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductDetailService {

    Page<ProductDetail> getAllBySearchCriteria(DataTableRequest dataTableRequest);

    ProductDetail save(ProductDetail productDetail);

    List<ProductDetail> getAllByProductId(String productId);

    List<String> getAllSizeIdByProductId(String productId);

    List<String> getAllColorIdByProductId(String productId);

    List<String> getAllMaterialIdByProductId(String productId);

    List<ProductDetail> getAllProductDetailInOfStock();
}
