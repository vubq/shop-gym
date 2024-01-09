package com.web.shopgym.services.impl;

import com.web.shopgym.repositories.FeedbackRepository;
import com.web.shopgym.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public Double getRateProduct(String productId) {
        Double rate = this.feedbackRepository.getRateProduct(productId);
        return rate == null ? 0: rate;
    }
}
