package com.web.shopgym.services.impl;

import com.web.shopgym.entities.Size;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.repositories.SizeRepository;
import com.web.shopgym.services.SizeService;
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
public class SizeServiceImpl implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public List<Size> getAll() {
        return this.sizeRepository.findAll();
    }

    @Override
    public Page<Size> getListOfSizesByCriteria(DataTableRequest request, String status) {
        request.setSortBy("createdAt");
        request.setSortDesc(true);
        PageRequest pageable = request.toPageable();
        BaseSpecification<Size> specNameContains = new BaseSpecification<>(
                SearchCriteria.builder()
                        .keys(new String[]{Size.Fields.name})
                        .operation(SearchOperation.CONTAINS)
                        .value(request.getFilter().trim().toUpperCase())
                        .build());
        BaseSpecification<Size> specStatusEquality = new BaseSpecification<>(
                SearchCriteria.builder()
                        .keys(new String[]{Size.Fields.status})
                        .operation(SearchOperation.EQUALITY)
                        .value(status)
                        .build());
        return this.sizeRepository.findAll(Specification.where(specNameContains).and(specStatusEquality), pageable);
    }

    @Override
    public Optional<Size> findById(String id) {
        return this.sizeRepository.findById(id);
    }

    @Override
    public Size save(Size size) {
        return this.sizeRepository.save(size);
    }

    @Override
    public List<String> getAllByProductId(String productId) {
        return this.sizeRepository.getAllByProductId(productId);
    }

    @Override
    public List<Size> getProductInStock(String productId, String sizeId) {
        return this.sizeRepository.getProductInStock(productId, sizeId);
    }
}
