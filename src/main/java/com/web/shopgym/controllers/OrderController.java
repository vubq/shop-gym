package com.web.shopgym.controllers;

import com.web.shopgym.entities.Order;
import com.web.shopgym.entities.OrderDetail;
import com.web.shopgym.entities.Voucher;
import com.web.shopgym.enums.EStatus;
import com.web.shopgym.enums.EVoucherType;
import com.web.shopgym.payloads.request.OrderDto;
import com.web.shopgym.payloads.response.Response;
import com.web.shopgym.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private VoucherService voucherService;

    @PostMapping()
    public Response createInvoice(@RequestBody OrderDto dto) {

        Voucher voucher = this.voucherService.findById(dto.getVoucherId()).orElse(null);

        Order order = this.orderService.save(Order.builder()
                .type(dto.getType())
                .fullNameCustomer(dto.getFullNameCustomer())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .createdBy(this.userService.findById(dto.getCreatedBy()).get())
                .createdDate(new Date())
                .completedDate(new Date())
                .totalAmount(dto.getTotalAmount())
                .voucher(voucher)
                .status(dto.getStatus())
                .build());

        double totalAmountIsReduced = voucher != null ? voucher.getValue() : 0;
        double discountForProducts = 0;


        if(voucher != null && voucher.getType() == EVoucherType.MONEY) {
            discountForProducts = voucher.getValue() / dto.getOrderDetails().size();
        }

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

        return Response.build().ok().data("OK");
    }
}
