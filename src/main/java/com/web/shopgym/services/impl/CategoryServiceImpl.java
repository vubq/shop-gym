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
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Page<Category> getListOfCategoriesByCriteria(DataTableRequest request, String status) {
        PageRequest pageable = request.toPageable();
        BaseSpecification<Category> specNameContains = new BaseSpecification<>(
                SearchCriteria.builder()
                        .keys(new String[]{Category.Fields.name})
                        .operation(SearchOperation.CONTAINS)
                        .value(request.getFilter().trim().toUpperCase())
                        .build());
        BaseSpecification<Category> specStatusEquality = new BaseSpecification<>(
                SearchCriteria.builder()
                        .keys(new String[]{Category.Fields.status})
                        .operation(SearchOperation.EQUALITY)
                        .value(status)
                        .build());
        return this.categoryRepository.findAll(Specification.where(specNameContains).and(specStatusEquality), pageable);
    }

    @Override
    public Optional<Category> findById(String id) {
        return this.categoryRepository.findById(id);
    }
}
