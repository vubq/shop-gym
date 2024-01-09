package com.web.shopgym.repositories;

import com.web.shopgym.entities.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, String> {

    Page<ProductDetail> findAll(Specification<ProductDetail> spec, Pageable pageable);

    @Query(value = "SELECT pd FROM ProductDetail pd WHERE pd.product.id = :productId")
    List<ProductDetail> findAllByProductId(String productId);

    @Query(value = "SELECT DISTINCT pd.size.id FROM ProductDetail pd WHERE pd.product.id = :productId")
    List<String> findAllSizeIdByProductId(String productId);

    @Query(value = "SELECT DISTINCT pd.color.id FROM ProductDetail pd WHERE pd.product.id = :productId")
    List<String> findAllColorIdByProductId(String productId);

    @Query(value = "SELECT DISTINCT pd.material.id FROM ProductDetail pd WHERE pd.product.id = :productId")
    List<String> findAllMaterilIdByProductId(String productId);

    @Query(value = "SELECT pd FROM ProductDetail pd WHERE pd.quantity > 0")
    List<ProductDetail> getAllProductDetailInOfStock();

    @Query(value = "SELECT DISTINCT pd.size.id FROM ProductDetail pd WHERE pd.product.id = :productId AND pd.quantity > 0")
    List<String> findAllSizeIdByProductIdInOfStock(String productId);

    @Query(value = "SELECT DISTINCT pd.color.id FROM ProductDetail pd WHERE pd.product.id = :productId AND pd.quantity > 0")
    List<String> findAllColorIdByProductIdInOfStock(String productId);

    @Query(value = "SELECT DISTINCT pd.material.id FROM ProductDetail pd WHERE pd.product.id = :productId AND pd.quantity > 0")
    List<String> findAllMaterialIdByProductIdInOfStock(String productId);
}
