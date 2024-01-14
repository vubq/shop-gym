package com.web.shopgym.dtos;

import com.web.shopgym.entities.OrderDetail;
import com.web.shopgym.enums.EPaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderDTO {

    private String fullNameCustomer;
    private String phoneNumber;
    private String address;
    private String createdBy;
    private Double totalAmount;
    private String email;
    private EPaymentType paymentType;
    private String voucherId;
    private String city;
    private String district;
    private String ward;
    private String noteByCustomer;
    private List<OrderDetailDTO> orderDetails;
}
