package com.web.shopgym.controllers;

import com.web.shopgym.entities.Feedback;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.payloads.response.DataTableResponse;
import com.web.shopgym.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/webapi/feedback")
public class FeedBackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("get-list-of-feedbacks-by-criteria")
    public DataTableResponse getListOfFeedbacksByCriteria(
            DataTableRequest dataTableRequest,
            @RequestParam(value = "productId") String productId,
            @RequestParam(value = "rate", required = false) Double rate) {
        Page<Feedback> result = this.feedbackService.getListOfFeedbacksByCriteria(dataTableRequest, productId, rate);

        return DataTableResponse.build()
                .ok()
                .totalRows(result.getTotalElements())
                .items(result.get().toList());
    }
}
