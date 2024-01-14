package com.web.shopgym.services;

import com.web.shopgym.entities.Feedback;
import com.web.shopgym.payloads.request.DataTableRequest;
import org.springframework.data.domain.Page;

public interface FeedbackService {

    Page<Feedback> getListOfFeedbacksByCriteria(DataTableRequest request, String productId, Double rate);

    Double getRateProduct(String productId);
}
