package com.web.shopgym.controllers;

import com.web.shopgym.entities.Color;
import com.web.shopgym.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/webapi/color")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @GetMapping
    public List<Color> getAll() {

        return this.colorService.getAll();
    }
}
