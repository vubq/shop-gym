package com.web.shopgym.services.impl;

import com.web.shopgym.entities.OrderDetail;
import com.web.shopgym.repositories.OrderDetailRepository;
import com.web.shopgym.services.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> saveAll(List<OrderDetail> orderDetails) {
        return this.orderDetailRepository.saveAll(orderDetails);
    }

    @Override
    public List<OrderDetail> getListOfOrderDetailsByOrderId(String orderId) {
        return this.orderDetailRepository.findAllByOrderId(orderId);
    }
}
