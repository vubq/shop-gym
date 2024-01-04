package com.web.shopgym.services;

import com.web.shopgym.entities.Voucher;
import com.web.shopgym.payloads.request.DataTableRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface VoucherService {

    Optional<Voucher> findById(String id);

    Optional<Voucher> findByCode(String code);

    Page<Voucher> getListOfVouchersByCriteria(DataTableRequest request);

    Voucher save(Voucher voucher);
}
