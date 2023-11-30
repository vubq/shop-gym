package com.web.shopgym.services.impl;

import com.web.shopgym.entities.Product;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.repositories.ProductRepository;
import com.web.shopgym.services.ProductService;
import com.web.shopgym.utils.BaseSpecification;
import com.web.shopgym.utils.SearchCriteria;
import com.web.shopgym.utils.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> getAllBySearchCriteria(DataTableRequest dataTableRequest) {
        PageRequest pageable = dataTableRequest.toPageable();
        BaseSpecification<Product> specNameContains = new BaseSpecification<>(
                SearchCriteria.builder().keys(new String[]{ Product.Fields.name }).operation(SearchOperation.CONTAINS)
                        .value(dataTableRequest.getFilter().trim().toUpperCase()).build());

        return this.productRepository.findAll(Specification.where(specNameContains), pageable);
    }

    @Override
    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public Product getById(String id) {
        return this.productRepository.findById(id).get();
    }
}
