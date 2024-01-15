package com.web.shopgym.services.impl;

import com.web.shopgym.entities.Order;
import com.web.shopgym.enums.EOrderStatus;
import com.web.shopgym.enums.EOrderType;
import com.web.shopgym.payloads.request.DataTableRequest;
import com.web.shopgym.repositories.OrderRepository;
import com.web.shopgym.searchForm.OrderSearchForm;
import com.web.shopgym.services.OrderService;
import com.web.shopgym.utils.BaseSpecification;
import com.web.shopgym.utils.SearchCriteria;
import com.web.shopgym.utils.SearchOperation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public Page<Order> getListOfOrdersByCriteriaSellAtTheShop(DataTableRequest request, String dateFrom, String dateTo) {
        request.setSortBy("createdAt");
        request.setSortDesc(true);
        PageRequest pageable = request.toPageable();
        BaseSpecification<Order> specFullNameCustomerContains = new BaseSpecification<>(
                SearchCriteria.builder()
                        .keys(new String[]{Order.Fields.fullNameCustomer})
                        .operation(SearchOperation.CONTAINS)
                        .value(request.getFilter().trim().toUpperCase())
                        .build());
        BaseSpecification<Order> specTypeEquality = new BaseSpecification<>(
                SearchCriteria.builder()
                        .keys(new String[]{Order.Fields.type})
                        .operation(SearchOperation.EQUALITY)
                        .value(EOrderType.AT_THE_SHOP)
                        .build());
        BaseSpecification<Order> specDateFromGreaterThanOrEqual = new BaseSpecification<>(
                SearchCriteria.builder()
                        .keys(new String[]{Order.Fields.completedAt})
                        .operation(SearchOperation.GREATER_THAN_OR_EQUAL)
                        .value(dateFrom + " 00:00:00.000")
                        .build());
        BaseSpecification<Order> specDateToLessThanOrEqual = new BaseSpecification<>(
                SearchCriteria.builder()
                        .keys(new String[]{Order.Fields.completedAt})
                        .operation(SearchOperation.LESS_THAN_OR_EQUAL)
                        .value(dateTo + " 23:59:59.999")
                        .build());
        return this.orderRepository.findAll(
                Specification
                        .where(specFullNameCustomerContains)
                        .and(specTypeEquality)
                        .and(specDateFromGreaterThanOrEqual)
                        .and(specDateToLessThanOrEqual)
                , pageable);
    }

    @Override
    public List<Order> getAllOrderByUserId(String userId) {
        return this.orderRepository.getAllOrderByUserId(userId);
    }

    @Override
    public Page<Order> getListOfOrdersByCriteria(DataTableRequest request, OrderSearchForm orderSearchForm) {
        request.setSortBy("createdAt");
        request.setSortDesc(true);
        PageRequest pageable = request.toPageable();
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(orderSearchForm.getOrderStatus().size() > 0) {
                    predicates.add(criteriaBuilder.and(root.get(Order.Fields.status).in(orderSearchForm.getOrderStatus())));
                }
                query.where(predicates.toArray(new Predicate[]{}));
                return null;
            }
        };
        return this.orderRepository.findAll(specification, pageable);
    }

    @Override
    public Optional<Order> findById(String id) {
        return this.orderRepository.findById(id);
    }

    @Override
    public List<Order> getAllOrderWaitForConfirmationByUserId(String userId) {
        return this.orderRepository.getAllOrderWaitForConfirmationByUserId(userId);
    }

    @Override
    public List<Order> getAllOrderCancelledByUserId(String userId) {
        return this.orderRepository.getAllOrderCancelledByUserId(userId);
    }

    @Override
    public List<Order> getAllOrderSuccessByUserId(String userId) {
        return this.orderRepository.getAllOrderSuccessByUserId(userId);
    }
}
