package com.web.shopgym.services.impl;

import com.web.shopgym.entities.Material;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.repositories.MaterialRepository;
import com.web.shopgym.services.MaterialService;
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
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public List<Material> getAll() {
        return this.materialRepository.findAll();
    }

    @Override
    public Page<Material> getListOfMaterialsByCriteria(DataTableRequest request, String status) {
        request.setSortBy("createdAt");
        request.setSortDesc(true);
        PageRequest pageable = request.toPageable();
        BaseSpecification<Material> specNameContains = new BaseSpecification<>(
                SearchCriteria.builder()
                        .keys(new String[]{Material.Fields.name})
                        .operation(SearchOperation.CONTAINS)
                        .value(request.getFilter().trim().toUpperCase())
                        .build());
        BaseSpecification<Material> specStatusEquality = new BaseSpecification<>(
                SearchCriteria.builder()
                        .keys(new String[]{Material.Fields.status})
                        .operation(SearchOperation.EQUALITY)
                        .value(status)
                        .build());
        return this.materialRepository.findAll(Specification.where(specNameContains).and(specStatusEquality), pageable);
    }

    @Override
    public Optional<Material> findById(String id) {
        return this.materialRepository.findById(id);
    }

    @Override
    public Material save(Material material) {
        return this.materialRepository.save(material);
    }

    @Override
    public List<String> getAllByProductId(String productId) {
        return this.materialRepository.getAllByProductId(productId);
    }

    @Override
    public List<Material> getProductInStock(String productId, String materialId) {
        return this.materialRepository.getProductInStock(productId, materialId);
    }
}
