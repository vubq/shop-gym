package com.web.shopgym.services.impl;

import com.web.shopgym.entities.Color;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.repositories.ColorRepository;
import com.web.shopgym.services.ColorService;
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
public class ColorServiceImpl implements ColorService {

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public List<Color> getAll() {
        return this.colorRepository.findAll();
    }

    @Override
    public Page<Color> getListOfColorsByCriteria(DataTableRequest request, String status) {
        request.setSortBy("createdAt");
        request.setSortDesc(true);
        PageRequest pageable = request.toPageable();
        BaseSpecification<Color> specNameContains = new BaseSpecification<>(
                SearchCriteria.builder()
                        .keys(new String[]{Color.Fields.name})
                        .operation(SearchOperation.CONTAINS)
                        .value(request.getFilter().trim().toUpperCase())
                        .build());
        BaseSpecification<Color> specStatusEquality = new BaseSpecification<>(
                SearchCriteria.builder()
                        .keys(new String[]{Color.Fields.status})
                        .operation(SearchOperation.EQUALITY)
                        .value(status)
                        .build());
        return this.colorRepository.findAll(Specification.where(specNameContains).and(specStatusEquality), pageable);
    }

    @Override
    public Optional<Color> findById(String id) {
        return this.colorRepository.findById(id);
    }

    @Override
    public Color save(Color color) {
        return this.colorRepository.save(color);
    }
}
