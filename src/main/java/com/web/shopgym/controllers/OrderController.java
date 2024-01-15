package com.web.shopgym.controllers;

import com.cloudinary.utils.StringUtils;
import com.web.shopgym.dtos.CreateOrderDTO;
import com.web.shopgym.dtos.OrderByUserDTO;
import com.web.shopgym.entities.*;
import com.web.shopgym.enums.*;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.payloads.request.OrderDto;
import com.web.shopgym.payloads.response.DataTableResponse;
import com.web.shopgym.payloads.response.Response;
import com.web.shopgym.searchForm.OrderSearchForm;
import com.web.shopgym.services.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

    @Autowired
    private VoucherService voucherService;

    @PostMapping()
    @Transactional
    public Response createInvoice(@RequestBody OrderDto dto) {


        Voucher voucher;
        if (!StringUtils.isEmpty(dto.getVoucherId())) {
            voucher = this.voucherService.findById(dto.getVoucherId()).get();
        } else {
            voucher = null;
        }

        double totalAmount = dto.getOrderDetails().stream()
                .mapToDouble(orderDetail -> orderDetail.getQuantity() * this.productDetailService.findById(orderDetail.getProductDetailId()).get().getPrice())
                .sum();

        double discountForProducts = 0;

        if (voucher != null && voucher.getType() == EVoucherType.MONEY) {
            discountForProducts = voucher.getValue() / dto.getOrderDetails().size();
            totalAmount = totalAmount - voucher.getValue();
        }
        if (voucher != null && voucher.getType() == EVoucherType.PERCENT) {
            discountForProducts = (totalAmount * voucher.getValue() / 100) / dto.getOrderDetails().size();
            totalAmount = totalAmount - totalAmount * voucher.getValue() / 100;
        }

        if(voucher != null) {
            voucher.setQuantity(voucher.getQuantity() - 1);
            this.voucherService.save(voucher);
        }

        Order order = this.orderService.save(Order.builder()
                .type(dto.getType())
                .fullNameCustomer(dto.getFullNameCustomer())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .createdBy(this.userService.findById(dto.getCreatedBy()).get())
                .createdAt(new Date())
                .completedAt(new Date())
                .totalAmount(totalAmount)
                .voucherId(voucher.getId())
                .paymentType(EPaymentType.PAYMENT_IN_SHOP)
                .status(EOrderStatus.SUCCESS)
                .build());

        List<OrderDetail> orderDetails = new ArrayList<>();
        List<ProductDetail> productDetails = new ArrayList<>();
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
                    .createdAt(new Date())
                    .status(EStatus.ACTIVE)
                    .productPrice(productDetail.getPrice())
                    .build());
            productDetail.setQuantity(productDetail.getQuantity() - orderDetail.getQuantity());
            productDetails.add(productDetail);
        });

        this.orderDetailService.saveAll(orderDetails);
        this.productDetailService.saveAll(productDetails);
        return Response.build().ok().data("OK");
    }

    @GetMapping("get-list-of-orders-by-criteria-sell-at-the-shop")
    public DataTableResponse getListOfOrdersByCriteriaSellAtTheShop(DataTableRequest request, @RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo) {
        Page<Order> result = this.orderService.getListOfOrdersByCriteriaSellAtTheShop(request, dateFrom, dateTo);

        return DataTableResponse.build()
                .ok()
                .totalRows(result.getTotalElements())
                .items(result.get().toList());
    }

    @PostMapping("get-list-of-orders-by-criteria")
    public DataTableResponse getListOfOrdersByCriteria(DataTableRequest request, @RequestBody OrderSearchForm orderSearchForm) {
        Page<Order> result = this.orderService.getListOfOrdersByCriteria(request, orderSearchForm);
        List<OrderByUserDTO> orderByUser = new ArrayList<>();
        result.getContent().forEach(order -> {
            orderByUser.add(OrderByUserDTO.builder()
                    .id(order.getId())
                    .type(order.getType())
                    .fullNameCustomer(order.getFullNameCustomer())
                    .phoneNumber(order.getPhoneNumber())
                    .address(order.getAddress())
                    .email(order.getEmail())
                    .createdBy(order.getCreatedBy())
                    .createdAt(order.getCreatedAt())
                    .completedAt(order.getCompletedAt())
                    .totalAmount(order.getTotalAmount())
                    .voucherId(order.getVoucherId())
                    .noteByCustomer(order.getNoteByCustomer())
                    .city(order.getCity())
                    .district(order.getDistrict())
                    .ward(order.getWard())
                    .noteByAdmin(order.getNoteByAdmin())
                    .paymentType(order.getPaymentType())
                    .status(order.getStatus())
                    .orderDetails(this.orderDetailService.getListOfOrderDetailsByOrderId(order.getId()))
                    .build());
        });
        return DataTableResponse.build()
                .ok()
                .totalRows(result.getTotalElements())
                .items(orderByUser);
    }

    @GetMapping("get-order-details-by-order-id/{orderId}")
    public Response getOrderDetailsByOrderId(@PathVariable(value = "orderId") String orderId) {
        Order order = this.orderService.findById(orderId).orElse(null);
        List<OrderDetail> orderDetails = this.orderDetailService.getListOfOrderDetailsByOrderId(orderId);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("order", order);
        hashMap.put("orderDetails", orderDetails);
        return Response.build().ok().data(hashMap);
    }

    @PostMapping("/create-order")
    @Transactional
    public Response createOrder(@RequestBody CreateOrderDTO dto) {
        Voucher voucher;
        if (!StringUtils.isEmpty(dto.getVoucherId())) {
            voucher = this.voucherService.findById(dto.getVoucherId()).get();
        } else {
            voucher = null;
        }

        double totalAmount = dto.getOrderDetails().stream()
                .mapToDouble(orderDetail -> orderDetail.getQuantity() * this.productDetailService.findById(orderDetail.getProductDetailId()).get().getPrice())
                .sum();

        double discountForProducts = 0;

        if (voucher != null && voucher.getType() == EVoucherType.MONEY) {
            discountForProducts = voucher.getValue() / dto.getOrderDetails().size();
            totalAmount = totalAmount - voucher.getValue();
        }
        if (voucher != null && voucher.getType() == EVoucherType.PERCENT) {
            discountForProducts = (totalAmount * voucher.getValue() / 100) / dto.getOrderDetails().size();
            totalAmount = totalAmount - totalAmount * voucher.getValue() / 100;
        }

        if(voucher != null) {
            voucher.setQuantity(voucher.getQuantity() - 1);
            this.voucherService.save(voucher);
        }

        Order order = this.orderService.save(Order.builder()
                .type(EOrderType.ONLINE)
                .fullNameCustomer(dto.getFullNameCustomer())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .email(dto.getEmail())
                .createdBy(this.userService.findById(dto.getCreatedBy()).get())
                .createdAt(new Date())
                .completedAt(new Date())
                .totalAmount(dto.getTotalAmount())
                .paymentType(dto.getPaymentType())
                .noteByCustomer(dto.getNoteByCustomer())
                .city(dto.getCity())
                .district(dto.getDistrict())
                .ward(dto.getWard())
                .status(EOrderStatus.WAIT_FOR_CONFIRMATION)
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
                    .createdAt(new Date())
                    .status(EStatus.ACTIVE)
                    .productPrice(productDetail.getPrice())
                    .build());
        });
        this.orderDetailService.saveAll(orderDetails);
        return Response.build().ok();
    }

    @GetMapping("/get-all-order-wait-for-confirmation-by-user-id/{userId}")
    public Response getAllOrderWaitForConfirmationByUserId(@PathVariable String userId) {
        List<Order> orders = this.orderService.getAllOrderWaitForConfirmationByUserId(userId);
        List<OrderByUserDTO> orderByUser = new ArrayList<>();
        orders.forEach(order -> {
            orderByUser.add(OrderByUserDTO.builder()
                    .id(order.getId())
                    .type(order.getType())
                    .fullNameCustomer(order.getFullNameCustomer())
                    .phoneNumber(order.getPhoneNumber())
                    .address(order.getAddress())
                    .email(order.getEmail())
                    .createdBy(order.getCreatedBy())
                    .createdAt(order.getCreatedAt())
                    .completedAt(order.getCompletedAt())
                    .totalAmount(order.getTotalAmount())
                    .voucherId(order.getVoucherId())
                    .noteByCustomer(order.getNoteByCustomer())
                    .city(order.getCity())
                    .district(order.getDistrict())
                    .ward(order.getWard())
                    .noteByAdmin(order.getNoteByAdmin())
                    .paymentType(order.getPaymentType())
                    .status(order.getStatus())
                    .orderDetails(this.orderDetailService.getListOfOrderDetailsByOrderId(order.getId()))
                    .build());
        });
        return Response.build().ok().data(orderByUser);
    }

    @GetMapping("/get-all-order-success-by-user-id/{userId}")
    public Response getAllOrderSuccessByUserId(@PathVariable String userId) {
        List<Order> orders = this.orderService.getAllOrderSuccessByUserId(userId);
        List<OrderByUserDTO> orderByUser = new ArrayList<>();
        orders.forEach(order -> {
            orderByUser.add(OrderByUserDTO.builder()
                    .id(order.getId())
                    .type(order.getType())
                    .fullNameCustomer(order.getFullNameCustomer())
                    .phoneNumber(order.getPhoneNumber())
                    .address(order.getAddress())
                    .email(order.getEmail())
                    .createdBy(order.getCreatedBy())
                    .createdAt(order.getCreatedAt())
                    .completedAt(order.getCompletedAt())
                    .totalAmount(order.getTotalAmount())
                    .voucherId(order.getVoucherId())
                    .noteByCustomer(order.getNoteByCustomer())
                    .city(order.getCity())
                    .district(order.getDistrict())
                    .ward(order.getWard())
                    .noteByAdmin(order.getNoteByAdmin())
                    .paymentType(order.getPaymentType())
                    .status(order.getStatus())
                    .orderDetails(this.orderDetailService.getListOfOrderDetailsByOrderId(order.getId()))
                    .build());
        });
        return Response.build().ok().data(orderByUser);
    }

    @GetMapping("/get-all-order-cancelled-by-user-id/{userId}")
    public Response getAllOrderCancelledByUserId(@PathVariable String userId) {
        List<Order> orders = this.orderService.getAllOrderCancelledByUserId(userId);
        List<OrderByUserDTO> orderByUser = new ArrayList<>();
        orders.forEach(order -> {
            orderByUser.add(OrderByUserDTO.builder()
                    .id(order.getId())
                    .type(order.getType())
                    .fullNameCustomer(order.getFullNameCustomer())
                    .phoneNumber(order.getPhoneNumber())
                    .address(order.getAddress())
                    .email(order.getEmail())
                    .createdBy(order.getCreatedBy())
                    .createdAt(order.getCreatedAt())
                    .completedAt(order.getCompletedAt())
                    .totalAmount(order.getTotalAmount())
                    .voucherId(order.getVoucherId())
                    .noteByCustomer(order.getNoteByCustomer())
                    .city(order.getCity())
                    .district(order.getDistrict())
                    .ward(order.getWard())
                    .noteByAdmin(order.getNoteByAdmin())
                    .paymentType(order.getPaymentType())
                    .status(order.getStatus())
                    .orderDetails(this.orderDetailService.getListOfOrderDetailsByOrderId(order.getId()))
                    .build());
        });
        return Response.build().ok().data(orderByUser);
    }

    @GetMapping("/get-all-order-by-user-id/{userId}")
    public Response getAllOrderByUserId(@PathVariable String userId) {
        List<Order> orders = this.orderService.getAllOrderByUserId(userId);
        List<OrderByUserDTO> orderByUser = new ArrayList<>();
        orders.forEach(order -> {
            orderByUser.add(OrderByUserDTO.builder()
                    .id(order.getId())
                    .type(order.getType())
                    .fullNameCustomer(order.getFullNameCustomer())
                    .phoneNumber(order.getPhoneNumber())
                    .address(order.getAddress())
                    .email(order.getEmail())
                    .createdBy(order.getCreatedBy())
                    .createdAt(order.getCreatedAt())
                    .completedAt(order.getCompletedAt())
                    .totalAmount(order.getTotalAmount())
                    .voucherId(order.getVoucherId())
                    .noteByCustomer(order.getNoteByCustomer())
                    .city(order.getCity())
                    .district(order.getDistrict())
                    .ward(order.getWard())
                    .noteByAdmin(order.getNoteByAdmin())
                    .paymentType(order.getPaymentType())
                    .status(order.getStatus())
                    .orderDetails(this.orderDetailService.getListOfOrderDetailsByOrderId(order.getId()))
                    .build());
        });
        return Response.build().ok().data(orderByUser);
    }

    @GetMapping("update-order")
    public Response updateOrder(@RequestParam(value = "orderId") String orderId, @RequestParam(value = "action") String action) {
        Order order = this.orderService.findById(orderId).get();
        if(action.equals("xacnhan")) {
            order.setStatus(EOrderStatus.PREPARING_GOODS);
            this.orderService.save(order);
        }
        if(action.equals("dagui")) {
            order.setStatus(EOrderStatus.DELIVERING);
            this.orderService.save(order);
        }
        if(action.equals("dagiao")) {
            order.setStatus(EOrderStatus.DELIVERED);
            this.orderService.save(order);
        }
        if(action.equals("huy")) {
            order.setStatus(EOrderStatus.CANCELLED);
            this.orderService.save(order);
        }
        if(action.equals("nhanhang")) {
            order.setStatus(EOrderStatus.SUCCESS);
            this.orderService.save(order);
        }
        return Response.build().ok();
    }
}
