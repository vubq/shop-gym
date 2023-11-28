package com.web.shopgym.services.impl;

import com.web.shopgym.entities.Category;
import com.web.shopgym.repositories.CategoryRepository;
import com.web.shopgym.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
