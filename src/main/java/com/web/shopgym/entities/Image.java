package com.web.shopgym.entities;

import com.web.shopgym.enums.EImageType;
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
@Table(name = "images")
public class Image {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "public_id")
    private String publicId;

    @Column(name = "url")
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EImageType type;

    @Column(name = "secondary_id")
    private String secondaryId;
}
