package com.web.shopgym.controllers;

import com.web.shopgym.dtos.SizeDTO;
import com.web.shopgym.entities.Size;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.payloads.response.DataTableResponse;
import com.web.shopgym.payloads.response.Response;
import com.web.shopgym.services.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @GetMapping("{id}")
    public Response getById(@PathVariable String id) {
        return Response.build().ok().data(this.sizeService.findById(id));
    }

    @PostMapping()
    public Response create(@RequestBody SizeDTO sizeDTO) {
        Size size = this.sizeService.save(Size.builder()
                .name(sizeDTO.getName())
                .description(sizeDTO.getDescription())
                .createdAt(new Date())
                .status(sizeDTO.getStatus())
                .build());
        return Response.build().ok().data(size);
    }

    @PutMapping()
    public Response update(@RequestBody SizeDTO sizeDTO) {
        Size size = this.sizeService.save(Size.builder()
                .id(sizeDTO.getId())
                .name(sizeDTO.getName())
                .description(sizeDTO.getDescription())
                .createdAt(sizeDTO.getCreatedAt())
                .status(sizeDTO.getStatus())
                .build());
        return Response.build().ok().data(size);
    }

    @GetMapping("get-list-of-sizes-by-criteria")
    public DataTableResponse getListOfSizesByCriteria(DataTableRequest request, @RequestParam(value = "status") String status) {
        Page<Size> result = this.sizeService.getListOfSizesByCriteria(request, status);

        return DataTableResponse.build()
                .ok()
                .totalRows(result.getTotalElements())
                .items(result.get().toList());
    }
}
