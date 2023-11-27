package com.web.shopgym.services.impl;

import com.web.shopgym.entities.Material;
import com.web.shopgym.repositories.MaterialRepository;
import com.web.shopgym.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public List<Material> getAll() {
        return this.materialRepository.findAll();
    }
}
