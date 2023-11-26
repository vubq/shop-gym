package com.web.shopgym.repositories;

import com.web.shopgym.entities.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, String> {

    public Page<ProductDetail> findAll(Specification<ProductDetail> spec, Pageable pageable);
}
