package com.web.shopgym.controllers;

import com.web.shopgym.dtos.VoucherDTO;
import com.web.shopgym.entities.Voucher;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.payloads.response.DataTableResponse;
import com.web.shopgym.payloads.response.Response;
import com.web.shopgym.services.UserService;
import com.web.shopgym.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/webapi/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private UserService userService;

    @GetMapping("code/{code}")
    public Voucher findByCode(@PathVariable(value = "code") String code) {
        return this.voucherService.findByCode(code).orElse(null);
    }

    @GetMapping("get-list-of-vouchers-by-criteria")
    public DataTableResponse getListOfSizesByCriteria(DataTableRequest request) {
        Page<Voucher> result = this.voucherService.getListOfVouchersByCriteria(request);

        return DataTableResponse.build()
                .ok()
                .totalRows(result.getTotalElements())
                .items(result.get().toList());
    }

    @GetMapping("{id}")
    public Response getById(@PathVariable String id) {
        return Response.build().ok().data(this.voucherService.findById(id));
    }

    @PostMapping()
    public Response create(@RequestBody VoucherDTO voucherDTO) {
        Voucher voucher = this.voucherService.save(Voucher.builder()
                .code(voucherDTO.getCode())
                .type(voucherDTO.getType())
                .startDate(voucherDTO.getStartDate())
                .endDate(voucherDTO.getEndDate())
                .createdAt(new Date())
                .createdBy(this.userService.findById(voucherDTO.getCreatedBy()).get())
                .value(voucherDTO.getValue())
                .status(voucherDTO.getStatus())
                .build());
        return Response.build().ok().data(voucher);
    }

    @PutMapping()
    public Response update(@RequestBody VoucherDTO voucherDTO) {
        Voucher voucher = this.voucherService.save(Voucher.builder()
                .id(voucherDTO.getId())
                .status(voucherDTO.getStatus())
                .build());
        return Response.build().ok().data(voucher);
    }
}
