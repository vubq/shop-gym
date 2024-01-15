package com.web.shopgym.repositories;

import com.web.shopgym.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    Page<Order> findAll(Specification<Order> spec, Pageable pageable);

    @Query(value = "SELECT o FROM Order o WHERE o.createdBy.id = :userId AND o.status = com.web.shopgym.enums.EOrderStatus.WAIT_FOR_CONFIRMATION")
    List<Order> getAllOrderWaitForConfirmationByUserId(String userId);

    @Query(value = "SELECT o FROM Order o WHERE o.createdBy.id = :userId AND o.status = com.web.shopgym.enums.EOrderStatus.SUCCESS")
    List<Order> getAllOrderSuccessByUserId(String userId);

    @Query(value = "SELECT o FROM Order o WHERE o.createdBy.id = :userId AND o.status = com.web.shopgym.enums.EOrderStatus.CANCELLED")
    List<Order> getAllOrderCancelledByUserId(String userId);

    @Query(value = "SELECT o FROM Order o WHERE o.createdBy.id = :userId")
    List<Order> getAllOrderByUserId(String userId);
}
