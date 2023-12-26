package com.web.shopgym.payloads.request;

import com.web.shopgym.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDto {

    public String id;
    public String orderId;
    public String productDetailId;
    public Integer quantity;
    public Double totalAmount;
    public boolean isVoucher;
    public EStatus status;
}
