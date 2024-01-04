package com.web.shopgym.services;

import com.web.shopgym.entities.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    List<OrderDetail> saveAll(List<OrderDetail> orderDetails);

    List<OrderDetail> getListOfOrderDetailsByOrderId(String orderId);
}
