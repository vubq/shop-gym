package com.web.shopgym.controllers;

import com.web.shopgym.entities.Category;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.payloads.response.DataTableResponse;
import com.web.shopgym.payloads.response.Response;
import com.web.shopgym.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/webapi/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAll() {
        return this.categoryService.getAll();
    }

    @GetMapping("{id}")
    public Response getById(@PathVariable String id) {
        return Response.build().ok().data(this.categoryService.findById(id));
    }

    @GetMapping("get-list-of-categories-by-criteria")
    public DataTableResponse getListOfCategoriesByCriteria(DataTableRequest request, @RequestParam(value = "status") String status) {
        Page<Category> result = this.categoryService.getListOfCategoriesByCriteria(request, status);

        return DataTableResponse.build()
                .ok()
                .totalRows(result.getTotalElements())
                .items(result.get().toList());
    }
}
