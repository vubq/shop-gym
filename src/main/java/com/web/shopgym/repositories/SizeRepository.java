package com.web.shopgym.repositories;

import com.web.shopgym.entities.Material;
import com.web.shopgym.entities.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, String> {

    Page<Size> findAll(Specification<Size> spec, Pageable pageable);

    @Query(value = "SELECT DISTINCT(s.id) FROM Size s JOIN ProductDetail pd ON s.id = pd.size.id WHERE pd.product.id = :productId")
    List<String> getAllByProductId(String productId);

    @Query(value = "SELECT s FROM Size s JOIN ProductDetail pd ON s.id = pd.size.id WHERE pd.product.id = :productId AND s.id = :sizeId AND pd.quantity > 0")
    List<Size> getProductInStock(String productId, String sizeId);
}
