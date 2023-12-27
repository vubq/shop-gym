package com.web.shopgym.controllers;

import com.cloudinary.utils.StringUtils;
import com.web.shopgym.entities.Order;
import com.web.shopgym.entities.OrderDetail;
import com.web.shopgym.entities.ProductDetail;
import com.web.shopgym.entities.Voucher;
import com.web.shopgym.enums.EOrderStatus;
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

        Voucher voucher;
        if(!StringUtils.isEmpty(dto.getVoucherId())) {
            voucher = this.voucherService.findById(dto.getVoucherId()).get();
        } else {
            voucher = null;
        }

        double totalAmount = dto.getOrderDetails().stream()
                .mapToDouble(orderDetail -> orderDetail.getQuantity() * this.productDetailService.findById(orderDetail.getProductDetailId()).get().getPrice())
                .sum();

        double discountForProducts = 0;

        if(voucher != null && voucher.getType() == EVoucherType.MONEY) {
            discountForProducts = voucher.getValue() / dto.getOrderDetails().size();
            totalAmount = totalAmount - voucher.getValue();
        }
        if(voucher != null && voucher.getType() == EVoucherType.PERCENT) {
            discountForProducts = (totalAmount - totalAmount * voucher.getValue() / 100) / dto.getOrderDetails().size();
            totalAmount = totalAmount - totalAmount * voucher.getValue() / 100;
        }

        Order order = this.orderService.save(Order.builder()
                .type(dto.getType())
                .fullNameCustomer(dto.getFullNameCustomer())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .createdBy(this.userService.findById(dto.getCreatedBy()).get())
                .createdDate(new Date())
                .completedDate(new Date())
                .totalAmount(totalAmount)
                .voucher(voucher)
                .status(EOrderStatus.SUCCESS)
                .build());

        List<OrderDetail> orderDetails = new ArrayList<>();
        double finalDiscountForProducts = discountForProducts;
        dto.getOrderDetails().forEach(orderDetail -> {
            ProductDetail productDetail = this.productDetailService.findById(orderDetail.getProductDetailId()).get();
            double total = orderDetail.getQuantity() * productDetail.getPrice() - finalDiscountForProducts;
            orderDetails.add(OrderDetail.builder()
                    .order(order)
                    .productDetail(productDetail)
                    .quantity(orderDetail.getQuantity())
                    .totalAmount(total > 0 ? total : 0)
                    .isVoucher(voucher == null ? false : true)
                    .status(EStatus.ACTIVE)
                    .build());
        });

        this.orderDetailService.saveAll(orderDetails);

        return Response.build().ok().data("OK");
    }
}
