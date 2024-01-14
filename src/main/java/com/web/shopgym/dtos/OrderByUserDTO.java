package com.web.shopgym.dtos;

import com.web.shopgym.entities.Order;
import com.web.shopgym.entities.OrderDetail;
import com.web.shopgym.entities.User;
import com.web.shopgym.enums.EOrderStatus;
import com.web.shopgym.enums.EOrderType;
import com.web.shopgym.enums.EPaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderByUserDTO {

    private String id;
    private EOrderType type;
    private String fullNameCustomer;
    private String phoneNumber;
    private String address;
    private String email;
    private User createdBy;
    private Date createdAt;
    private Date completedAt;
    private Double totalAmount;
    private String voucherId;
    private String noteByCustomer;
    private String city;
    private String district;
    private String ward;
    private String noteByAdmin;
    private EPaymentType paymentType;
    private EOrderStatus status;
    private List<OrderDetail> orderDetails;
}
