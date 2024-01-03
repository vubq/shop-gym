package com.web.shopgym.controllers;

import com.web.shopgym.dtos.ColorDTO;
import com.web.shopgym.entities.Color;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.payloads.response.DataTableResponse;
import com.web.shopgym.payloads.response.Response;
import com.web.shopgym.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @GetMapping("{id}")
    public Response getById(@PathVariable String id) {
        return Response.build().ok().data(this.colorService.findById(id));
    }

    @PostMapping()
    public Response create(@RequestBody ColorDTO colorDTO) {
        Color color = this.colorService.save(Color.builder()
                .name(colorDTO.getName())
                .description(colorDTO.getDescription())
                .createdAt(new Date())
                .status(colorDTO.getStatus())
                .build());
        return Response.build().ok().data(color);
    }

    @PutMapping()
    public Response update(@RequestBody ColorDTO colorDTO) {
        Color color = this.colorService.save(Color.builder()
                .id(colorDTO.getId())
                .name(colorDTO.getName())
                .description(colorDTO.getDescription())
                .createdAt(colorDTO.getCreatedAt())
                .status(colorDTO.getStatus())
                .build());
        return Response.build().ok().data(color);
    }

    @GetMapping("get-list-of-colors-by-criteria")
    public DataTableResponse getListOfColorsByCriteria(DataTableRequest request, @RequestParam(value = "status") String status) {
        Page<Color> result = this.colorService.getListOfColorsByCriteria(request, status);

        return DataTableResponse.build()
                .ok()
                .totalRows(result.getTotalElements())
                .items(result.get().toList());
    }
}
