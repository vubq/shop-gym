package com.web.shopgym.services.impl;

import com.web.shopgym.entities.Order;
import com.web.shopgym.repositories.OrderRepository;
import com.web.shopgym.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return this.orderRepository.save(order);
    }
}
