package com.web.shopgym.repositories;

import com.web.shopgym.entities.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ColorRepository extends JpaRepository<Color, String> {

    Page<Color> findAll(Specification<Color> spec, Pageable pageable);

    @Query(value = "SELECT DISTINCT(c.id) FROM Color c JOIN ProductDetail pd ON c.id = pd.color.id WHERE pd.product.id = :productId")
    List<String> getAllByProductId(String productId);

    @Query(value = "SELECT c FROM Color c JOIN ProductDetail pd ON c.id = pd.color.id WHERE pd.product.id = :productId AND c.id = :colorId AND pd.quantity > 0")
    List<Color> getProductInStock(String productId, String colorId);
}
