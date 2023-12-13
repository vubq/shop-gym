package com.web.shopgym.repositories;

import com.web.shopgym.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    @Query(value = "SELECT DISTINCT p.id FROM Product p JOIN ProductDetail pd ON p.id = pd.product.id WHERE pd.quantity > 0")
    List<String> getAllProductIdInOfStock();
}
