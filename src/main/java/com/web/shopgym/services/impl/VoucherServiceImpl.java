package com.web.shopgym.services.impl;

import com.web.shopgym.entities.Voucher;
import com.web.shopgym.repositories.VoucherRepository;
import com.web.shopgym.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public Optional<Voucher> findById(String id) {
        return this.voucherRepository.findById(id);
    }

    @Override
    public Optional<Voucher> findByCode(String code) {
        return this.voucherRepository.findByCode(code);
    }
}
