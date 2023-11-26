package com.web.shopgym.entities;

import com.web.shopgym.enums.EOrderStatus;
import com.web.shopgym.enums.EOrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EOrderType type;

    @Column(name = "full_name_customer")
    private String fullNameCustomer;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "completed_date")
    private Date completedDate;

    @Column(name = "total_amount")
    private Double totalAmount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    private Voucher voucher;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EOrderStatus status;
}
