package com.web.shopgym.services.impl;

import com.web.shopgym.entities.Size;
import com.web.shopgym.repositories.SizeRepository;
import com.web.shopgym.services.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public List<Size> getAll() {
        return this.sizeRepository.findAll();
    }
}
