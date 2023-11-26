package com.web.shopgym.entities;

import com.web.shopgym.enums.EStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Entity
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_detail_id", nullable = false)
    private ProductDetail productDetail;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "is_voucher")
    private Boolean isVoucher;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EStatus status;
}
