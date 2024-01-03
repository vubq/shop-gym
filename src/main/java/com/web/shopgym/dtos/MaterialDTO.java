package com.web.shopgym.dtos;

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
public class MaterialDTO {

    private String id;
    private String name;
    private String description;
    private Date createdAt;
    private EStatus status;
}