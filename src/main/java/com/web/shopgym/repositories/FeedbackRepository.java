package com.web.shopgym.repositories;

import com.web.shopgym.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {

    @Query(value = "SELECT AVG(f.rate) FROM Feedback f WHERE f.product.id = :productId")
    Double getRateProduct(String productId);
}
