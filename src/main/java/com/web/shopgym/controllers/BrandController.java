package com.web.shopgym.controllers;

import com.web.shopgym.entities.Brand;
import com.web.shopgym.dtos.BrandDTO;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.payloads.response.DataTableResponse;
import com.web.shopgym.payloads.response.Response;
import com.web.shopgym.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/webapi/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public List<Brand> getAll() {

        return this.brandService.getAll();
    }

    @GetMapping("{id}")
    public Response getById(@PathVariable String id) {
        return Response.build().ok().data(this.brandService.findById(id));
    }

    @PostMapping()
    public Response create(@RequestBody BrandDTO brandDTO) {
        Brand brand = this.brandService.save(Brand.builder()
                .name(brandDTO.getName())
                .description(brandDTO.getDescription())
                .createdAt(new Date())
                .status(brandDTO.getStatus())
                .build());
        return Response.build().ok().data(brand);
    }

    @PutMapping()
    public Response update(@RequestBody BrandDTO brandDTO) {
        Brand brand = this.brandService.save(Brand.builder()
                .id(brandDTO.getId())
                .name(brandDTO.getName())
                .description(brandDTO.getDescription())
                .createdAt(brandDTO.getCreatedAt())
                .status(brandDTO.getStatus())
                .build());
        return Response.build().ok().data(brand);
    }

    @GetMapping("get-list-of-brands-by-criteria")
    public DataTableResponse getListOfBrandsByCriteria(DataTableRequest request, @RequestParam(value = "status") String status) {
        Page<Brand> result = this.brandService.getListOfBrandsByCriteria(request, status);

        return DataTableResponse.build()
                .ok()
                .totalRows(result.getTotalElements())
                .items(result.get().toList());
    }
}
