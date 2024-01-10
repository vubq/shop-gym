package com.web.shopgym.dtos;

import com.web.shopgym.entities.Color;
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
public class ColorDTO {

    private String id;
    private String name;
    private String description;
    private Date createdAt;
    private EStatus status;
    private Boolean isOutOfStock;

    public static ColorDTO toDto(Color color) {
        return ColorDTO.builder()
                .id(color.getId())
                .name(color.getName())
                .description(color.getDescription())
                .status(color.getStatus())
                .createdAt(color.getCreatedAt())
                .build();
    }
}