package com.web.shopgym.controllers;

import com.web.shopgym.entities.Size;
import com.web.shopgym.entities.Voucher;
import com.web.shopgym.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/webapi/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("{code}")
    public Voucher findByCode(@PathVariable(value = "code") String code) {
        return this.voucherService.findByCode(code).orElse(null);
    }
}
