package com.web.shopgym.payloads.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductWebShopRequest {

    List<String> sizes;
    List<String> color;
    List<String> materials;
    List<String> categories;
}
