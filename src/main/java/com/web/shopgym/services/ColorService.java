package com.web.shopgym.services;

import com.web.shopgym.entities.Color;
import com.web.shopgym.payloads.request.DataTableRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ColorService {

    List<Color> getAll();

    Page<Color> getListOfColorsByCriteria(DataTableRequest request, String status);

    Optional<Color> findById(String id);

    Color save(Color color);

    List<String> getAllByProductId(String productId);

    List<Color> getProductInStock(String productId, String colorId);
}
