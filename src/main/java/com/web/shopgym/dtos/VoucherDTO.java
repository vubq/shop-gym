package com.web.shopgym.dtos;

import com.web.shopgym.enums.EStatus;
import com.web.shopgym.enums.EVoucherType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherDTO {

    private String id;
    private String code;
    private EVoucherType type;
    private Double value;
    private Date startDate;
    private Date endDate;
    private String createdBy;
    private Date createdAt;
    private EStatus status;
}
