package com.web.shopgym.services;

import com.web.shopgym.entities.Product;
import com.web.shopgym.payloads.request.DataTableRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Page<Product> getListOfProductsByCriteria(DataTableRequest dataTableRequest, String status);

    Product save(Product product);

    Product getById(String id);

    List<String> getAllProductIdInOfStock();
}
