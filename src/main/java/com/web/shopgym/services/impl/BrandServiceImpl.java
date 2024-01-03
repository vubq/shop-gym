package com.web.shopgym.services.impl;

import com.web.shopgym.entities.Brand;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.repositories.BrandRepository;
import com.web.shopgym.services.BrandService;
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
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> getAll() {
        return this.brandRepository.findAll();
    }

    @Override
    public Page<Brand> getListOfBrandsByCriteria(DataTableRequest request, String status) {
        request.setSortBy("createdAt");
        request.setSortDesc(true);
        PageRequest pageable = request.toPageable();
        BaseSpecification<Brand> specNameContains = new BaseSpecification<>(
                SearchCriteria.builder()
                        .keys(new String[]{Brand.Fields.name})
                        .operation(SearchOperation.CONTAINS)
                        .value(request.getFilter().trim().toUpperCase())
                        .build());
        BaseSpecification<Brand> specStatusEquality = new BaseSpecification<>(
                SearchCriteria.builder()
                        .keys(new String[]{Brand.Fields.status})
                        .operation(SearchOperation.EQUALITY)
                        .value(status)
                        .build());
        return this.brandRepository.findAll(Specification.where(specNameContains).and(specStatusEquality), pageable);
    }

    @Override
    public Optional<Brand> findById(String id) {
        return this.brandRepository.findById(id);
    }

    @Override
    public Brand save(Brand brand) {
        return this.brandRepository.save(brand);
    }
}
