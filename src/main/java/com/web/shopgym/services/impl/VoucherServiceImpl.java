package com.web.shopgym.services.impl;

import com.web.shopgym.entities.Size;
import com.web.shopgym.entities.Voucher;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.repositories.VoucherRepository;
import com.web.shopgym.services.VoucherService;
import com.web.shopgym.utils.BaseSpecification;
import com.web.shopgym.utils.SearchCriteria;
import com.web.shopgym.utils.SearchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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

    @Override
    public Page<Voucher> getListOfVouchersByCriteria(DataTableRequest request) {
        request.setSortBy("createdAt");
        request.setSortDesc(true);
        PageRequest pageable = request.toPageable();
        BaseSpecification<Voucher> specCodeContains = new BaseSpecification<>(
                SearchCriteria.builder()
                        .keys(new String[]{Voucher.Fields.code})
                        .operation(SearchOperation.CONTAINS)
                        .value(request.getFilter().trim().toUpperCase())
                        .build());
        return this.voucherRepository.findAll(Specification.where(specCodeContains), pageable);
    }

    @Override
    public Voucher save(Voucher voucher) {
        return this.voucherRepository.save(voucher);
    }
}
