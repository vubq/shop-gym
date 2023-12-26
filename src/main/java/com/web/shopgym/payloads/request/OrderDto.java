package com.web.shopgym.payloads.request;

import com.web.shopgym.enums.EOrderStatus;
import com.web.shopgym.enums.EOrderType;
import com.web.shopgym.enums.EVoucherType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private String id;
    private EOrderType type;
    private String fullNameCustomer;
    private String phoneNumber;
    private String address;
    private String createdBy;
    private Date createdDate;
    private Date completedDate;
    private Double totalAmount;
    private String voucherId;
    private EOrderStatus status;

    public List<OrderDetailDto> orderDetails;
}
