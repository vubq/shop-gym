package com.web.shopgym.entities;

import com.web.shopgym.enums.EStatus;
import com.web.shopgym.enums.EUserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Builder
@Entity
@Table(name="categories")
public class Category {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EStatus status;
}
