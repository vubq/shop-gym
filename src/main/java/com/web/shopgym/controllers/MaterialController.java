package com.web.shopgym.controllers;

import com.web.shopgym.dtos.MaterialDTO;
import com.web.shopgym.entities.Material;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.payloads.response.DataTableResponse;
import com.web.shopgym.payloads.response.Response;
import com.web.shopgym.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/webapi/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping
    public List<Material> getAll() {

        return this.materialService.getAll();
    }

    @GetMapping("{id}")
    public Response getById(@PathVariable String id) {
        return Response.build().ok().data(this.materialService.findById(id));
    }

    @PostMapping()
    public Response create(@RequestBody MaterialDTO materialDTO) {
        Material material = this.materialService.save(Material.builder()
                .name(materialDTO.getName())
                .description(materialDTO.getDescription())
                .createdAt(new Date())
                .status(materialDTO.getStatus())
                .build());
        return Response.build().ok().data(material);
    }

    @PutMapping()
    public Response update(@RequestBody MaterialDTO materialDTO) {
        Material material = this.materialService.save(Material.builder()
                .id(materialDTO.getId())
                .name(materialDTO.getName())
                .description(materialDTO.getDescription())
                .createdAt(materialDTO.getCreatedAt())
                .status(materialDTO.getStatus())
                .build());
        return Response.build().ok().data(material);
    }

    @GetMapping("get-list-of-materials-by-criteria")
    public DataTableResponse getListOfMaterialsByCriteria(DataTableRequest request, @RequestParam(value = "status") String status) {
        Page<Material> result = this.materialService.getListOfMaterialsByCriteria(request, status);

        return DataTableResponse.build()
                .ok()
                .totalRows(result.getTotalElements())
                .items(result.get().toList());
    }
}
