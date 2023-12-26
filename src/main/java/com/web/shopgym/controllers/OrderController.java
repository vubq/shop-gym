package com.web.shopgym.controllers;

import com.web.shopgym.entities.Order;
import com.web.shopgym.entities.OrderDetail;
import com.web.shopgym.enums.EStatus;
import com.web.shopgym.payloads.request.OrderDto;
import com.web.shopgym.payloads.response.Response;
import com.web.shopgym.services.OrderDetailService;
import com.web.shopgym.services.OrderService;
import com.web.shopgym.services.ProductDetailService;
import com.web.shopgym.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/webapi/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductDetailService productDetailService;

    @PostMapping()
    public Response createInvoice(@RequestBody OrderDto dto) {

        Order order = this.orderService.save(Order.builder()
                .type(dto.getType())
                .fullNameCustomer(dto.getFullNameCustomer())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .createdBy(this.userService.findById(dto.getCreatedBy()).get())
                .createdDate(new Date())
                .completedDate(new Date())
                .totalAmount(dto.getTotalAmount())
                .voucher(null)
                .status(dto.getStatus())
                .build());

        List<OrderDetail> orderDetails = new ArrayList<>();
        dto.getOrderDetails().forEach(orderDetail -> {
            orderDetails.add(OrderDetail.builder()
                    .order(order)
                    .productDetail(this.productDetailService.findById(orderDetail.getProductDetailId()).get())
                    .quantity(orderDetail.getQuantity())
                    .totalAmount(orderDetail.getTotalAmount())
                    .isVoucher(false)
                    .status(EStatus.ACTIVE)
                    .build());
        });

        this.orderDetailService.saveAll(orderDetails);

        return Response.build().ok().data(this.orderDetailService.saveAll(orderDetails));
    }
}
