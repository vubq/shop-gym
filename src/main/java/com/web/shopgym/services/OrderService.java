package com.web.shopgym.services;

import com.web.shopgym.entities.Order;
import com.web.shopgym.enums.EOrderStatus;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.searchForm.OrderSearchForm;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order save(Order order);

    Page<Order> getListOfOrdersByCriteriaSellAtTheShop(DataTableRequest request, String dateFrom, String dateTo);

    Page<Order> getListOfOrdersByCriteria(DataTableRequest request, OrderSearchForm orderSearchForm);

    Optional<Order> findById(String id);

    List<Order> getAllOrderWaitForConfirmationByUserId(String userId);

    List<Order> getAllOrderCancelledByUserId(String userId);

    List<Order> getAllOrderSuccessByUserId(String userId);

    List<Order> getAllOrderByUserId(String userId);
}
