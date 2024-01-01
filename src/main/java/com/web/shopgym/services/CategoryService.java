package com.web.shopgym.services;

import com.web.shopgym.entities.Category;
import com.web.shopgym.payloads.request.DataTableRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Page<Category> getListOfCategoriesByCriteria(DataTableRequest request);
}
