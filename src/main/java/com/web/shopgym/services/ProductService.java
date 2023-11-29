package com.web.shopgym.services;

import com.web.shopgym.entities.Product;
import com.web.shopgym.payloads.request.DataTableRequest;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<Product> getAllBySearchCriteria(DataTableRequest dataTableRequest);

    Product save(Product product);
}
