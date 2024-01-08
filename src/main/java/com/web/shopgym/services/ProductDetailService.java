package com.web.shopgym.services;

import com.web.shopgym.entities.Product;
import com.web.shopgym.entities.ProductDetail;
import com.web.shopgym.payloads.request.DataTableRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductDetailService {

    Optional<ProductDetail> findById(String id);

    Page<ProductDetail> getAllBySearchCriteria(DataTableRequest dataTableRequest);

    Page<ProductDetail> getListOfProductDetailsByCriteriaWebShop(
            DataTableRequest dataTableRequest,
            List<String> categories,
            List<String> sizes,
            List<String> colors,
            List<String> materials,
            List<Double> priceApprox);

    ProductDetail save(ProductDetail productDetail);

    List<ProductDetail> getAllByProductId(String productId);

    List<String> getAllSizeIdByProductId(String productId);

    List<String> getAllColorIdByProductId(String productId);

    List<String> getAllMaterialIdByProductId(String productId);

    List<ProductDetail> getAllProductDetailInOfStock();

    List<ProductDetail> saveAll(List<ProductDetail> productDetails);
}
