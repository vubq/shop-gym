package com.web.shopgym.services;

import com.web.shopgym.entities.Voucher;

import java.util.Optional;

public interface VoucherService {

    Optional<Voucher> findById(String id);

    Optional<Voucher> findByCode(String code);
}
