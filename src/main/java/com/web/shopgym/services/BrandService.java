package com.web.shopgym.services;

import com.web.shopgym.entities.Brand;
import com.web.shopgym.entities.Category;
import com.web.shopgym.payloads.request.DataTableRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BrandService {

    List<Brand> getAll();

    Page<Brand> getListOfBrandsByCriteria(DataTableRequest request, String status);

    Optional<Brand> findById(String id);

    Brand save(Brand brand);
}
