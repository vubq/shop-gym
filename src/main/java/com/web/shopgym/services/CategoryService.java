package com.web.shopgym.services;

import com.web.shopgym.entities.Category;
import com.web.shopgym.payloads.request.DataTableRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getAll();

    Page<Category> getListOfCategoriesByCriteria(DataTableRequest request, String status);

    Optional<Category> findById(String id);
}
