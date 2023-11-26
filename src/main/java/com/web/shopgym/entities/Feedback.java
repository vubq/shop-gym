package com.web.shopgym.entities;

import com.web.shopgym.enums.EStatus;
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
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "content")
    private String content;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EStatus status;
}
