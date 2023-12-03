package com.web.shopgym.services.impl;

import com.web.shopgym.entities.ProductDetail;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.repositories.ProductDetailRepository;
import com.web.shopgym.services.ProductDetailService;
import com.web.shopgym.utils.BaseSpecification;
import com.web.shopgym.utils.SearchCriteria;
import com.web.shopgym.utils.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public Page<ProductDetail> getAllBySearchCriteria(DataTableRequest dataTableRequest) {
        PageRequest pageable = dataTableRequest.toPageable();
        BaseSpecification<ProductDetail> specCodeContains = new BaseSpecification<>(
                SearchCriteria.builder().keys(new String[]{ProductDetail.Fields.code}).operation(SearchOperation.CONTAINS)
                        .value(dataTableRequest.getFilter().trim().toUpperCase()).build());

        return this.productDetailRepository.findAll(Specification.where(specCodeContains), pageable);
    }

    @Override
    public ProductDetail save(ProductDetail productDetail) {
        return this.productDetailRepository.save(productDetail);
    }

    @Override
    public List<ProductDetail> getAllByProductId(String productId) {
        return this.productDetailRepository.findAllByProductId(productId);
    }

    @Override
    public List<String> getAllSizeIdByProductId(String productId) {
        return this.productDetailRepository.findAllSizeIdByProductId(productId);
    }

    @Override
    public List<String> getAllColorIdByProductId(String productId) {
        return this.productDetailRepository.findAllColorIdByProductId(productId);
    }

    @Override
    public List<String> getAllMaterialIdByProductId(String productId) {
        return this.productDetailRepository.findAllMaterilIdByProductId(productId);
    }
}
