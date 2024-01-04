package com.web.shopgym.controllers;

import com.web.shopgym.payloads.response.Response;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/webapi/order-detail")
public class OrderDetailController {

    @GetMapping ("/get-list-of-order-details-by-order-id/{orderId}")
    public Response getListOfOrderDetailsByOrderId(@PathVariable(value = "orderId") String orderId) {
        return Response.build().ok();
    }
}
