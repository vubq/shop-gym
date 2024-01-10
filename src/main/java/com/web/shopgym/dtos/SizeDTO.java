package com.web.shopgym.dtos;

import com.web.shopgym.entities.Size;
import com.web.shopgym.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SizeDTO {

    private String id;
    private String name;
    private String description;
    private Date createdAt;
    private EStatus status;
    private Boolean isOutOfStock;

    public static SizeDTO toDto(Size color) {
        return SizeDTO.builder()
                .id(color.getId())
                .name(color.getName())
                .description(color.getDescription())
                .status(color.getStatus())
                .createdAt(color.getCreatedAt())
                .build();
    }
}
