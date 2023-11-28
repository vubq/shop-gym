package com.web.shopgym.services.impl;

import com.web.shopgym.entities.Brand;
import com.web.shopgym.repositories.BrandRepository;
import com.web.shopgym.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> getAll() {
        return this.brandRepository.findAll();
    }
}
