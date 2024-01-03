package com.web.shopgym.services;

import com.web.shopgym.entities.Size;
import com.web.shopgym.payloads.request.DataTableRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SizeService {

    List<Size> getAll();

    Page<Size> getListOfSizesByCriteria(DataTableRequest request, String status);

    Optional<Size> findById(String id);

    Size save(Size size);
}
