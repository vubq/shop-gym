package com.web.shopgym.controllers;

import com.web.shopgym.entities.Size;
import com.web.shopgym.services.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/webapi/size")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @GetMapping
    public List<Size> getAll() {

        return this.sizeService.getAll();
    }
}
