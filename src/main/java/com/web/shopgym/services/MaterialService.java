package com.web.shopgym.services;

import com.web.shopgym.entities.Material;
import com.web.shopgym.payloads.request.DataTableRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface MaterialService {

    List<Material> getAll();

    Page<Material> getListOfMaterialsByCriteria(DataTableRequest request, String status);

    Optional<Material> findById(String id);

    Material save(Material material);

    List<String> getAllByProductId(String productId);

    List<Material> getProductInStock(String productId, String materialId);
}
