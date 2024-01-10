package com.web.shopgym.repositories;

import com.web.shopgym.entities.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, String> {

    Page<Material> findAll(Specification<Material> spec, Pageable pageable);

    @Query(value = "SELECT DISTINCT(m.id) FROM Material m JOIN ProductDetail pd ON m.id = pd.material.id WHERE pd.product.id = :productId")
    List<String> getAllByProductId(String productId);

    @Query(value = "SELECT m FROM Material m JOIN ProductDetail pd ON m.id = pd.material.id WHERE pd.product.id = :productId AND m.id = :materialId AND pd.quantity > 0")
    List<Material> getProductInStock(String productId, String materialId);
}
