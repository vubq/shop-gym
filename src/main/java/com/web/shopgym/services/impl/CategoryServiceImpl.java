package com.web.shopgym.services.impl;

import com.web.shopgym.entities.Category;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.repositories.CategoryRepository;
import com.web.shopgym.services.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Page<Category> getListOfCategoriesByCriteria(DataTableRequest request) {
        PageRequest pageable = request.toPageable();
        BaseSpecification<Category> specNameContains = new BaseSpecification<>(
                SearchCriteria.builder()
                        .keys(new String[]{Category.Fields.name})
                        .operation(SearchOperation.CONTAINS)
                        .value(request.getFilter().trim().toUpperCase())
                        .build());
        return this.categoryRepository.findAll(Specification.where(specNameContains), pageable);
    }
}
