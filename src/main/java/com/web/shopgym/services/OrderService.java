package com.web.shopgym.services;

import com.web.shopgym.entities.Order;
import com.web.shopgym.payloads.request.DataTableRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface OrderService {

    Order save(Order order);

    Page<Order> getListOfOrdersByCriteriaSellAtTheShop(DataTableRequest request, String dateFrom, String dateTo);

    Optional<Order> findById(String id);
}
