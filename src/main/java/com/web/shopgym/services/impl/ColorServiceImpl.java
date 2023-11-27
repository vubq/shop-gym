package com.web.shopgym.services.impl;

import com.web.shopgym.entities.Color;
import com.web.shopgym.repositories.ColorRepository;
import com.web.shopgym.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ColorServiceImpl implements ColorService {

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public List<Color> getAll() {
        return this.colorRepository.findAll();
    }
}
